package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.core.utils.StringUtils;
import com.touchspring.smartforecasting.dao.SysOrganizationDao;
import com.touchspring.smartforecasting.dao.SysUserOrganizationDao;
import com.touchspring.smartforecasting.domain.dto.UserAuthDTO;
import com.touchspring.smartforecasting.domain.entity.sys.SysOrganization;
import com.touchspring.smartforecasting.service.SysOrganizationService;
import com.touchspring.smartforecasting.utils.JWTUtils;
import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrganizationController {


    @Autowired
    private SysOrganizationDao sysOrganizationDao;
    @Autowired
    private SysUserOrganizationDao sysUserOrganizationDao;
    @Autowired
    private SysOrganizationService sysOrganizationService;

    /**
     * 分页查询信息
     *
     * @return .
     */
    @GetMapping("relation-organizations")
    public ResultData findRelationOrganizations(
            @RequestHeader("token") String token) {
        UserAuthDTO sysUser = JWTUtils.unSign(token, UserAuthDTO.class);
        List<SysOrganization> sysOrganizations = sysOrganizationService.findByUserId(sysUser.getId());
        return ResultData.ok().putDataValue("organizations", sysOrganizations);
    }

    /**
     * 分页查询信息
     *
     * @param page      .
     * @param size      .
     * @param sort      .
     * @param direction .
     * @return .
     */
    @GetMapping("organizations")
    public ResultData findAllOrganizations(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "createAt") String sort,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction
    ) {
        PageResult sysDictDatePage;
        if (StringUtils.equals("ASC", direction.toUpperCase())) {
            direction = "ASC";
        } else {
            direction = "DESC";
        }
        sysDictDatePage = sysOrganizationService.findPage(new PageRequestOfMybatis(page, size),direction);
        long totalAmount = sysDictDatePage.getTotalSize();
        List<SysOrganization> sysOrganizations = (List<SysOrganization>)sysDictDatePage.getContent();
        return ResultData.ok().putDataValue("organizations", sysOrganizations).putDataValue("totalAmount", totalAmount);
    }

    /**
     * 根据id查询
     *
     * @param id .
     * @return .
     */
    @GetMapping("organizations/{id}")
    public ResultData findLineById(@PathVariable("id") String id) {
        SysOrganization organizationOptional = sysOrganizationDao.findById(id);
        if (organizationOptional==null) {
            return ResultData.notFound();
        }
        return ResultData.ok().putDataValue("organization", organizationOptional);
    }

    /**
     * 增加
     *
     * @param sysOrganization .
     * @return .
     */
    @PostMapping("organizations")
    public ResultData insertLine(@RequestBody SysOrganization sysOrganization) {
        sysOrganizationService.save(sysOrganization);
        return ResultData.ok().putDataValue("organizations", sysOrganization);
    }

    /**
     * 更新信息
     *
     * @param sysOrganization .
     * @return .
     */
    @PatchMapping("organizations/{id}")
    public ResultData updateLine(@PathVariable("id") String id, @RequestBody SysOrganization sysOrganization) {
        SysOrganization organizationOptional = sysOrganizationDao.findById(id);
        if (organizationOptional==null) {
            return ResultData.notFound();
        }
        SysOrganization target = organizationOptional;
        BeanUtils.copyProperties(sysOrganization, target, "id", "createAt", "updateAt", "createUserId", "updateUserId", "isDeleted");
        sysOrganizationDao.update(target);
        return ResultData.ok();
    }

    /**
     * 根据Id删除
     *
     * @param id .
     * @return .
     */
    @DeleteMapping("organizations/{id}")
    public ResultData deleteLine(@PathVariable("id") String id) {
        sysOrganizationDao.deleteById(id);
        return ResultData.ok();
    }
}
