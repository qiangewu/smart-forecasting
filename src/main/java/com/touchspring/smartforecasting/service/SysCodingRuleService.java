package com.touchspring.smartforecasting.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touchspring.smartforecasting.dao.SysCodingRuleDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysCodingRule;
import com.touchspring.smartforecasting.domain.entity.sys.SysCodingRuleDetail;
import com.touchspring.smartforecasting.repository.sys.SysCodingRuleDetailRepository;
import com.touchspring.smartforecasting.repository.sys.SysCodingRuleRepository;
import com.touchspring.smartforecasting.service.base.BaseService;
import com.touchspring.smartforecasting.service.base.PageHelperService;
import com.touchspring.smartforecasting.utils.IdWorker;
import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;
import com.touchspring.smartforecasting.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class SysCodingRuleService implements BaseService<SysCodingRule>, PageHelperService {
    @Autowired
    private SysCodingRuleRepository sysCodingRuleRepository;
    @Autowired
    private SysCodingRuleDetailRepository sysCodingRuleDetailRepository;
    @Autowired
    private SysCodingRuleDao sysCodingRuleDao;




    private Long getAutoValue(SysCodingRuleDetail sysCodingRuleDetail) {
        String currentValue = sysCodingRuleDetail.getCurrentValue();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime updateAt = sysCodingRuleDetail.getUpdateAt();
        boolean dayEq = now.getDayOfYear() == updateAt.getDayOfYear();
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 4);
        boolean weekEq = now.get(weekFields.weekOfWeekBasedYear()) == updateAt.get(weekFields.weekOfWeekBasedYear());
        boolean monthEq = now.getMonthValue() == updateAt.getMonthValue();
        boolean yearEq = now.getYear() == updateAt.getYear();

        switch (sysCodingRuleDetail.getCycle()) {
            case DAY:
                if (!(yearEq && monthEq && weekEq && dayEq)) {
                    currentValue = "0";
                }
            case WEEK:
                if (!(yearEq && monthEq && weekEq)) {
                    currentValue = "0";
                }
            case MONTH:
                if (!(yearEq && monthEq)) {
                    currentValue = "0";
                }
            case YEAR:
                if (!(yearEq)) {
                    currentValue = "0";
                }
            default:
                break;
        }

        Long current = Long.valueOf(currentValue);

        Long step = Long.valueOf(sysCodingRuleDetail.getStep());

        return current + step;
    }

    private String getNext(SysCodingRuleDetail sysCodingRuleDetail) {
        StringBuilder sb = new StringBuilder();
        switch (sysCodingRuleDetail.getType()) {
            case AUTO:
                String value = String.format("%0" + sysCodingRuleDetail.getLength() + "d", getAutoValue(sysCodingRuleDetail));
                sysCodingRuleDetail.setCurrentValue(value);
                sb.append(value);
                break;
            case TEXT:
                sb.append(sysCodingRuleDetail.getNode());
                break;
            case DATETIME:
                sb.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern(sysCodingRuleDetail.getNode())));
                break;
            default:
                break;
        }

        return sb.toString();
    }

    public synchronized String generatorCode(String code) {
        StringBuilder sb = new StringBuilder();
        Optional<SysCodingRule> sysCodingRuleOptional = sysCodingRuleRepository.findByCode(code);
        if (!sysCodingRuleOptional.isPresent()) {
            return "";
        }
        SysCodingRule sysCodingRule = sysCodingRuleOptional.get();
        List<SysCodingRuleDetail> sysCodingRuleDetails = sysCodingRuleDetailRepository.findBySysCodingRuleIdOrderBySortAsc(sysCodingRule.getId());
        sysCodingRuleDetails.forEach(x -> {
            sb.append(getNext(x));
            sysCodingRuleDetailRepository.save(x);
        });
        return sb.toString();
    }

    @Override
    public int save(SysCodingRule sysCodingRule) {
        int num;
        if(sysCodingRule.getId()==null){
            sysCodingRule.setId(IdWorker.nextId());
            num = sysCodingRuleDao.insert(sysCodingRule);
        }else {
            num = sysCodingRuleDao.update(sysCodingRule);
        }
        return num;
    }

    @Override
    public PageResult findPage(PageRequestOfMybatis pageRequestOfMybatis, String direction) {
        return PageUtils.getPageResult(pageRequestOfMybatis, getPageInfo(pageRequestOfMybatis, direction));
    }

    /**
     * 调用分页插件完成分页
     * @param pageRequestOfMybatis
     * @return
     */
    private PageInfo<SysCodingRule> getPageInfo(PageRequestOfMybatis pageRequestOfMybatis, String direction) {
        int pageNum = pageRequestOfMybatis.getPageNum();
        int pageSize = pageRequestOfMybatis.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SysCodingRule> list = sysCodingRuleDao.selectPage(direction);
        return new PageInfo<SysCodingRule>(list);
    }

}
