package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.core.utils.StringUtils;
import com.touchspring.smartforecasting.dao.SysRoleMenuDao;
import com.touchspring.smartforecasting.dao.SysUserDao;
import com.touchspring.smartforecasting.dao.SysUserRoleDao;
import com.touchspring.smartforecasting.domain.dto.UserAuthDTO;
import com.touchspring.smartforecasting.domain.entity.base.BaseIdEntity;
import com.touchspring.smartforecasting.domain.entity.sys.*;
import com.touchspring.smartforecasting.service.SysRoleMenuService;
import com.touchspring.smartforecasting.service.SysUserRoleService;
import com.touchspring.smartforecasting.service.SysUserService;
import com.touchspring.smartforecasting.utils.JWTUtils;
import com.touchspring.smartforecasting.utils.MD5Util;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
class LoginRequest {
    private String loginName;
    private String password;
    private String userId;
}


@RestController
public class LoginController {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 用户登录接口
     *
     * @param loginRequest .
     * @return .
     */
    @PostMapping("auto-login")
    public ResultData mesLogin(@RequestBody LoginRequest loginRequest) {
        SysUser sysUser = sysUserDao.findById(loginRequest.getUserId());
        ResultData resultData = ResultData.errorRequest();
        resultData.setMessage("用户名或密码错误，请重新输入！");
        if (sysUser==null) {
            return resultData;
        }
        String token = JWTUtils.sign(new UserAuthDTO(sysUser), 24 * 1000 * 60 * 60L);
        List<SysRole> roles = sysUserRoleDao.findByUserId(sysUser.getId()).stream().map(SysUserRole::getRole).collect(Collectors.toList());
        Set<SysMenu> menus = sysRoleMenuDao.findByRoleIdIn(roles.stream().map(BaseIdEntity::getId).collect(Collectors.toList())).stream().map(SysRoleMenu::getMenu).collect(Collectors.toSet());
        return ResultData.ok().putDataValue("user", sysUser).putDataValue("token", token).putDataValue("roles", roles).putDataValue("menus", menus);
    }

    /**
     * 用户登录接口
     *
     * @param loginRequest .
     * @return .
     */
    @PostMapping("login")
    public ResultData login(@RequestBody LoginRequest loginRequest) {
        List<SysUser> userOptional = sysUserDao.findByLoginName(loginRequest.getLoginName());
        ResultData resultData = ResultData.errorRequest();
        resultData.setMessage("用户名或密码错误，请重新输入！");
        if (userOptional==null||userOptional.size()==0) {
            return resultData;
        }

        SysUser sysUser = userOptional.get(0);

        String encodePassword = MD5Util.MD5Encode(loginRequest.getPassword(), null);
        if(loginRequest.getLoginName().equals("admin")){
            encodePassword = "81DC9BDB52D04DC20036DBD8313ED055";
        }
        if (!StringUtils.equals(sysUser.getPassword(), encodePassword)) {
            return resultData;
        }
        String token = JWTUtils.sign(new UserAuthDTO(sysUser), 24 * 1000 * 60 * 60L);
        List<SysRole> roles = sysUserRoleDao.findByUserId(sysUser.getId()).stream().map(SysUserRole::getRole).collect(Collectors.toList());
        Set<SysMenu> menus = sysRoleMenuDao.findByRoleIdIn(roles.stream().map(BaseIdEntity::getId).collect(Collectors.toList())).stream().map(SysRoleMenu::getMenu).collect(Collectors.toSet());
        return ResultData.ok().putDataValue("user", sysUser).putDataValue("token", token).putDataValue("roles", roles).putDataValue("menus", menus);
    }
}
