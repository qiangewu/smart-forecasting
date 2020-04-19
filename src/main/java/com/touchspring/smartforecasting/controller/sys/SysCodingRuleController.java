package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.core.utils.StringUtils;
import com.touchspring.smartforecasting.dao.SysCodingRuleDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysCodingRule;
import com.touchspring.smartforecasting.service.SysCodingRuleService;
import com.touchspring.smartforecasting.utils.IdWorker;
import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SysCodingRuleController {


    @Autowired
    private SysCodingRuleDao sysCodingRuleDao;
    @Autowired
    private SysCodingRuleService sysCodingRuleService;

    /**
     * 分页查询信息
     *
     * @param page      .
     * @param size      .
     * @param sort      .
     * @param direction .
     * @return .
     */
    @GetMapping("sys-coding-rules")
    public ResultData findAllSysCodingRules(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "createAt") String sort,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        PageResult sysPage;
        if (StringUtils.equals("ASC", direction.toUpperCase())) {
            direction = "ASC";
        } else {
            direction = "DESC";
        }
        sysPage = sysCodingRuleService.findPage(new PageRequestOfMybatis(page, size),direction);;
        long totalAmount = sysPage.getTotalSize();
        List<SysCodingRule> sysCodingRules = (List<SysCodingRule>)sysPage.getContent();
        return ResultData.ok().putDataValue("sysCodingRules", sysCodingRules).putDataValue("totalAmount", totalAmount);
    }

    /**
     * 根据id查询
     *
     * @param id .
     * @return .
     */
    @GetMapping("sys-coding-rules/{id}")
    public ResultData findSysCodingRuleById(@PathVariable("id") String id) {
        SysCodingRule sysCodingRule = sysCodingRuleDao.findById(id);
        if (sysCodingRule == null) {
            return ResultData.notFound();
        }
        return ResultData.ok().putDataValue("sysCodingRule", sysCodingRule);
    }

    /**
     * 增加
     *
     * @param sysCodingRule .
     * @return .
     */
    @PostMapping("sys-coding-rules")
    public ResultData insertSysCodingRule(@RequestBody SysCodingRule sysCodingRule) {
        sysCodingRule.setId(IdWorker.nextId());
        sysCodingRuleDao.insert(sysCodingRule);
        return ResultData.ok().putDataValue("sysCodingRules", sysCodingRule);
    }

    /**
     * 更新信息
     *
     * @param sysCodingRule .
     * @return .
     */
    @PatchMapping("sys-coding-rules/{id}")
    public ResultData updateSysCodingRule(@PathVariable("id") String id, @RequestBody SysCodingRule sysCodingRule) {
        SysCodingRule target = sysCodingRuleDao.findById(id);
        if (target == null) {
            return ResultData.notFound();
        }
        BeanUtils.copyProperties(sysCodingRule, target, "id", "createAt", "updateAt", "createUserId", "updateUserId", "isDeleted");
        sysCodingRuleDao.update(target);
        return ResultData.ok();
    }

    /**
     * 根据Id删除
     *
     * @param id .
     * @return .
     */
    @DeleteMapping("sys-coding-rules/{id}")
    public ResultData deleteSysCodingRule(@PathVariable("id") String id) {
        sysCodingRuleDao.deleteById(id);
        return ResultData.ok();
    }
}
