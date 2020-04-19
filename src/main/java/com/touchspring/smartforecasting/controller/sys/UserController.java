package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.core.utils.StringUtils;
import com.touchspring.smartforecasting.dao.SysUserDao;
import com.touchspring.smartforecasting.dao.SysUserOrganizationDao;
import com.touchspring.smartforecasting.dao.SysUserRoleDao;
import com.touchspring.smartforecasting.domain.dto.UserAuthDTO;
import com.touchspring.smartforecasting.domain.entity.sys.SysUser;
import com.touchspring.smartforecasting.domain.entity.sys.SysUserOrganization;
import com.touchspring.smartforecasting.domain.entity.sys.SysUserRole;
import com.touchspring.smartforecasting.domain.enums.SimpleEnums;
import com.touchspring.smartforecasting.service.SysCodingRuleService;
import com.touchspring.smartforecasting.service.SysLogService;
import com.touchspring.smartforecasting.service.SysUserService;
import com.touchspring.smartforecasting.utils.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Data
class UserRequest {
    private String id;
    private String userName;
    private String loginName;
    private String password;
    private SimpleEnums isActive;
    private String email;
    private String sex;
    private String[] roleIds;
    private String[] organizationIds;
}


@Data
class PasswordRequest {
    private String oldPassword;
    private String newPassword;
}

@RestController
public class UserController {
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysUserOrganizationDao sysUserOrganizationDao;
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysCodingRuleService sysCodingRuleService;
    @Autowired
    private SysLogService sysLogService;


    @PostMapping("async-users")
    public ResultData asyncUsers() {
//        sysUserService.asyncUsersFromMES();
        return ResultData.ok();
    }

    /**
     * @param page      .
     * @param size      .
     * @param sort      .
     * @param direction .
     * @return .
     */
    @GetMapping("users")
    public ResultData findAllUsers(
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
        sysPage = sysUserService.findPage(new PageRequestOfMybatis(page, size),direction);
        long totalAmount = sysPage.getTotalSize();
        List<SysUser> sysUsers =(List<SysUser>)sysPage.getContent();
        return ResultData.ok().putDataValue("users", sysUsers).putDataValue("totalAmount", totalAmount);
    }


    /**
     * 根据用户id查询用户信息
     *
     * @param id .
     * @return .
     */
    @GetMapping("users/{id}")
    public ResultData findUserById(@PathVariable("id") String id) {
        SysUser sysUser = sysUserDao.findById(id);
        if (sysUser==null) {
            return ResultData.notFound();
        }
        List<SysUserRole> roles = sysUserRoleDao.findByUserId(sysUser.getId());
        List<SysUserOrganization> organizations = sysUserOrganizationDao.findBySysUserId(sysUser.getId());
        return ResultData.ok().putDataValue("user", sysUser).putDataValue("roles", roles).putDataValue("organizations", organizations);
    }


    /**
     * 增加用户信息
     *
     * @param userRequest .
     * @return .
     */
    @PostMapping("users")
    public ResultData insertUser(HttpServletRequest request, @RequestHeader("token") String token, @RequestBody UserRequest userRequest) {
        UserAuthDTO userAuth = JWTUtils.unSign(token, UserAuthDTO.class);

        List<SysUser> loginName = sysUserDao.findByLoginName(userRequest.getLoginName());
        if (loginName!=null&&loginName.size()>0) {
            return new ResultData(1400, "登录名重复，请重新输入！");
        }
        List<SysUser> userName = sysUserDao.findByUserName(userRequest.getUserName());
        if (userName!=null&&userName.size()>0) {
            return new ResultData(1400, "用户名重复，请重新输入！");
        }
        String md5PassWord = MD5Util.MD5Encode("1234", null);
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userRequest, sysUser, "id", "password", "createAt", "updateAt", "createUserId", "updateUserId", "isDeleted");
        sysUser.setPassword(md5PassWord);
        sysUser.setId(IdWorker.nextId());
        sysUser.setUserNo(sysCodingRuleService.generatorCode("USER"));
        sysUserDao.insert(sysUser);
        if (userRequest.getRoleIds() != null) {
            for (String roleId : userRequest.getRoleIds()) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setId(IdWorker.nextId());
                sysUserRole.setRoleId(roleId);
                sysUserRole.setUserId(sysUser.getId());
                sysUserRoleDao.insert(sysUserRole);
            }
            for (String organizationId : userRequest.getOrganizationIds()) {
                SysUserOrganization sysUserOrganization = new SysUserOrganization();
                sysUserOrganization.setId(IdWorker.nextId());
                sysUserOrganization.setSysUserId(sysUser.getId());
                sysUserOrganization.setOrganizationId(organizationId);
                sysUserOrganizationDao.insert(sysUserOrganization);
            }
        }
        sysLogService.log("[" + request.getMethod() + "]" + request.getRequestURI(), userAuth.getId(), "新增用户", sysUser, null, sysUser.getId(), new String[]{"loginName", "userName", "email", "userNo", "language", "isActive"});
        return ResultData.ok().putDataValue("user", sysUser);
    }

    /**
     * 更新用户信息
     *
     * @param userRequest .
     * @return .
     */
    @PatchMapping("users/{id}")
    public ResultData updateUser(HttpServletRequest request, @RequestHeader("token") String token, @RequestBody UserRequest userRequest, @PathVariable("id") String id) {
        UserAuthDTO userAuth = JWTUtils.unSign(token, UserAuthDTO.class);

        SysUser target = sysUserDao.findById(id);
        if (target==null) {
            return ResultData.notFound();
        }
        List<SysUser> loginName = sysUserDao.findByLoginName(userRequest.getLoginName());
        if (loginName!=null&&loginName.size()>0 && !(StringUtils.equals(id, loginName.get(0).getId()))) {
            return new ResultData(1400, "登录名重复，请重新输入！");
        }
        SysUser logTarget = new SysUser();
        BeanUtils.copyProperties(target, logTarget);
        BeanUtils.copyProperties(userRequest, target, "id", "password", "createAt", "updateAt", "createUserId", "updateUserId", "isDeleted");
        sysUserDao.update(target);
        sysUserRoleDao.deleteByUserId(target.getId());
        sysUserOrganizationDao.deleteBySysUserId(target.getId());
        if (userRequest.getRoleIds() != null) {
            for (String roleId : userRequest.getRoleIds()) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setId(IdWorker.nextId());
                sysUserRole.setRoleId(roleId);
                sysUserRole.setUserId(target.getId());
                sysUserRoleDao.insert(sysUserRole);
            }
            for (String organizationId : userRequest.getOrganizationIds()) {
                SysUserOrganization sysUserOrganziation = new SysUserOrganization();
                sysUserOrganziation.setId(IdWorker.nextId());
                sysUserOrganziation.setSysUserId(target.getId());
                sysUserOrganziation.setOrganizationId(organizationId);
                sysUserOrganizationDao.insert(sysUserOrganziation);
            }
        }
        sysLogService.log("[" + request.getMethod() + "]" + request.getRequestURI(), userAuth.getId(), "更新用户", target, logTarget, target.getId(), new String[]{"loginName", "userName", "email", "userNo", "language", "isActive"});
        return ResultData.ok();
    }

    /**
     * 根据用户id删除用户
     *
     * @param id .
     * @return .
     */
    @DeleteMapping("users/{id}")
    public ResultData deleteUser(@PathVariable("id") String id) {
        sysUserDao.deleteById(id);
        return ResultData.ok();
    }

    /**
     * 修改用户密码
     *
     * @param passwordRequest .
     * @return .
     */
    @PatchMapping("password")
    public ResultData updateUserPassword(@RequestBody PasswordRequest passwordRequest, @RequestHeader("token") String token) {
        UserAuthDTO sysUser = JWTUtils.unSign(token, UserAuthDTO.class);
        SysUser target = sysUserDao.findById(sysUser.getId());
        if (target==null) {
            return ResultData.notFound();
        }

        if (StringUtils.isEmpty(passwordRequest.getOldPassword()) || !StringUtils.equals(target.getPassword(), MD5Util.MD5Encode(passwordRequest.getOldPassword(), null))) {
            ResultData resultData = ResultData.errorRequest();
            resultData.setMessage("原密码输入错误");
            return resultData;
        }
        String md5PassWord = MD5Util.MD5Encode(passwordRequest.getNewPassword(), null);
        target.setPassword(md5PassWord);
        sysUserDao.update(target);
        return ResultData.ok();
    }

}
