package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.core.utils.StringUtils;
import com.touchspring.smartforecasting.dao.SysUserRoleDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysRole;
import com.touchspring.smartforecasting.domain.entity.sys.SysUserRole;
import com.touchspring.smartforecasting.service.SysUserRoleService;
import com.touchspring.smartforecasting.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserRoleController {
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * 根据userId查询用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("userRole/{userId}")
    public ResultData findUserById(@PathVariable("userId") String userId) {
        List<SysRole> roles = new ArrayList<>();
        List<SysUserRole> userRoles = sysUserRoleDao.findByUserId(userId);
        if ((null != userRoles) && (userRoles.size() > 0)) {
            for (SysUserRole userRole : userRoles) {
                SysRole role = userRole.getRole();
                roles.add(role);
            }
        }
        return ResultData.ok().putDataValue("userRoles", roles);
    }


    /**
     * 增加 编辑菜单角色关联表信息
     *
     * @param roleIds
     * @return
     */
    @PostMapping("userRole/{userId}")
    public ResultData insertUser(@PathVariable("userId") String userId, String roleIds) {

        sysUserRoleDao.deleteByUserId(userId);
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        if (!StringUtils.isEmpty(roleIds)) {
            String[] split = roleIds.split(",");
            List<SysUserRole> userRoles = new ArrayList<>();
            for (String roleId : split) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setId(IdWorker.nextId());
                sysUserRole.setRoleId(roleId);
                sysUserRole.setUserId(userId);
                userRoles.add(sysUserRole);
            }
            sysUserRoleDao.insertAll(sysUserRoles);
        }
        return ResultData.ok().putDataValue("userRoles", sysUserRoles);
    }

    /**
     * 增加用户信息
     *
     * @param sysUserRole
     * @return
     */
    @PostMapping("userRole")
    public ResultData insertUser(SysUserRole sysUserRole) {
        sysUserRole.setId(IdWorker.nextId());
        sysUserRoleDao.insert(sysUserRole);
        return ResultData.ok().putDataValue("users", sysUserRole);
    }


    /**
     * 根据用户id删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("userRole/{id}")
    public ResultData deleteUser(@PathVariable("id") String id) {
        sysUserRoleDao.deleteById(id);
        return ResultData.ok();
    }

}
