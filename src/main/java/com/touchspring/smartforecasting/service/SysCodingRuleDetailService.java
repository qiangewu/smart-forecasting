package com.touchspring.smartforecasting.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touchspring.smartforecasting.dao.SysCodingRuleDao;
import com.touchspring.smartforecasting.dao.SysCodingRuleDetailDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysCodingRuleDetail;
import com.touchspring.smartforecasting.service.base.BaseService;
import com.touchspring.smartforecasting.service.base.EntityInitService;
import com.touchspring.smartforecasting.service.base.PageHelperService;
import com.touchspring.smartforecasting.utils.IdWorker;
import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;
import com.touchspring.smartforecasting.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author byh
 */

@Service
@Slf4j
public class SysCodingRuleDetailService extends EntityInitService<SysCodingRuleDetail> implements PageHelperService, BaseService<SysCodingRuleDetail> {
    @Autowired
    private SysCodingRuleDao sysCodingRuleDao;
    @Autowired
    private SysCodingRuleDetailDao sysCodingRuleDetailDao;


    @Override
    public PageResult findPage(PageRequestOfMybatis pageRequestOfMybatis,String direction) {
        return PageUtils.getPageResult(pageRequestOfMybatis, getPageInfo(pageRequestOfMybatis,direction));
    }

    /**
     * 调用分页插件完成分页
     * @param pageRequestOfMybatis
     * @return
     */
    private PageInfo<SysCodingRuleDetail> getPageInfo(PageRequestOfMybatis pageRequestOfMybatis,String direction) {
        int pageNum = pageRequestOfMybatis.getPageNum();
        int pageSize = pageRequestOfMybatis.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SysCodingRuleDetail> list = sysCodingRuleDetailDao.selectPage(direction);
        return new PageInfo<SysCodingRuleDetail>(list);
    }


    @Override
    public int save(SysCodingRuleDetail sysCodingRuleDetail) {
        int num;
        if(sysCodingRuleDetail.getId()==null){
            preInsert(sysCodingRuleDetail);
            num = sysCodingRuleDetailDao.insert(sysCodingRuleDetail);
        }else {
            preUpdate(sysCodingRuleDetail);
            num = sysCodingRuleDetailDao.update(sysCodingRuleDetail);
        }
        return num;
    }
}
