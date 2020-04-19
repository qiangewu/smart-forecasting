package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.core.utils.StringUtils;
import com.touchspring.smartforecasting.dao.SysUserOrganizationDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysUserOrganization;
import com.touchspring.smartforecasting.service.SysUserOrganizationService;
import com.touchspring.smartforecasting.utils.IdWorker;
import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserOrganizationController {

    @Autowired
    private SysUserOrganizationDao sysUserOrganizationDao;
    @Autowired
    private SysUserOrganizationService sysUserOrganizationService;

    /**
     * 分页查询信息
     *
     * @param page      .
     * @param size      .
     * @param sort      .
     * @param direction .
     * @return .
     */
    @GetMapping("sys-user-organizations")
    public ResultData findAllSysUserOrganizations(
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
        sysPage = sysUserOrganizationService.findPage(new PageRequestOfMybatis(page, size),direction);
        long totalAmount = sysPage.getTotalSize();
        List<SysUserOrganization> sysUserOrganizations = (List<SysUserOrganization>)sysPage.getContent();
        return ResultData.ok().putDataValue("sysUserOrganizations", sysUserOrganizations).putDataValue("totalAmount", totalAmount);
    }

    /**
     * 根据id查询
     *
     * @param id .
     * @return .
     */
    @GetMapping("sys-user-organizations/{id}")
    public ResultData findSysUserOrganizationById(@PathVariable("id") String id) {
        SysUserOrganization sysUserOrganization = sysUserOrganizationDao.findById(id);
        if (sysUserOrganization==null) {
            return ResultData.notFound();
        }
        return ResultData.ok().putDataValue("sysUserOrganization", sysUserOrganization);
    }

    /**
     * 增加
     *
     * @param sysUserOrganization .
     * @return .
     */
    @PostMapping("sys-user-organizations")
    public ResultData insertSysUserOrganization(@RequestBody SysUserOrganization sysUserOrganization) {
        sysUserOrganization.setId(IdWorker.nextId());
        sysUserOrganizationDao.insert(sysUserOrganization);
        return ResultData.ok().putDataValue("sysUserOrganizations", sysUserOrganization);
    }

    /**
     * 更新信息
     *
     * @param sysUserOrganization .
     * @return .
     */
    @PatchMapping("sys-user-organizations/{id}")
    public ResultData updateSysUserOrganization(@PathVariable("id") String id, @RequestBody SysUserOrganization sysUserOrganization) {
        SysUserOrganization target = sysUserOrganizationDao.findById(id);
        if (target==null) {
            return ResultData.notFound();
        }
        BeanUtils.copyProperties(sysUserOrganization, target, "id", "createAt", "updateAt", "createUserId", "updateUserId", "isDeleted");
        sysUserOrganizationDao.update(target);
        return ResultData.ok();
    }

    /**
     * 根据Id删除
     *
     * @param id .
     * @return .
     */
    @DeleteMapping("sys-user-organizations/{id}")
    public ResultData deleteSysUserOrganization(@PathVariable("id") String id) {
        sysUserOrganizationDao.deleteById(id);
        return ResultData.ok();
    }
}
