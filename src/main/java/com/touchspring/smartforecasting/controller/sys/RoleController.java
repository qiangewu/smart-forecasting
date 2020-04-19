package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.core.utils.StringUtils;
import com.touchspring.smartforecasting.dao.SysRoleDao;
import com.touchspring.smartforecasting.dao.SysRoleMenuDao;
import com.touchspring.smartforecasting.dao.SysUserRoleDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysRole;
import com.touchspring.smartforecasting.domain.entity.sys.SysRoleMenu;
import com.touchspring.smartforecasting.domain.enums.SimpleEnums;
import com.touchspring.smartforecasting.service.SysRoleService;
import com.touchspring.smartforecasting.utils.IdWorker;
import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Data
class RoleRequest {
    private String id;
    private String name;
    private String description;
    private SimpleEnums isActive;
    private String[] menuIds;
}


@RestController
public class RoleController {
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 分页查询角色信息
     *
     * @param page      .
     * @param size      .
     * @param sort      .
     * @param direction .
     * @return .
     */
    @GetMapping("roles")
    public ResultData findAllRoles(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", defaultValue = "20") Integer size,
                                   @RequestParam(value = "sort", defaultValue = "createAt") String sort,
                                   @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        PageResult sysPage;
        if (StringUtils.equals("ASC", direction.toUpperCase())) {
            direction = "ASC";
        } else {
            direction = "DESC";
        }
        sysPage = sysRoleService.findPage(new PageRequestOfMybatis(page, size),direction);
        long totalAmount = sysPage.getTotalSize();
        List<SysRole> sysRoles = (List<SysRole>)sysPage.getContent();
        return ResultData.ok().putDataValue("roles", sysRoles).putDataValue("totalAmount", totalAmount);
    }


    /**
     * 根据用户id查询角色信息
     *
     * @param id .
     * @return .
     */
    @GetMapping("roles/{id}")
    public ResultData findRoleById(@PathVariable("id") String id) {
        Optional<SysRole> roleOptional = sysRoleDao.findById(id);
        if (roleOptional==null) {
            return ResultData.notFound();
        }
        SysRole role = roleOptional.get();
        List<SysRoleMenu> menus = sysRoleMenuDao.findByRoleId(role.getId());
        return ResultData.ok().putDataValue("role", role).putDataValue("menus", menus);
    }


    /**
     * 增加角色信息
     *
     * @param roleRequest .
     * @return .
     */
    @PostMapping("roles")
    public ResultData insertRole(@RequestBody RoleRequest roleRequest) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(roleRequest, role);
        sysRoleService.save(role);
        if (roleRequest.getMenuIds() != null) {
            for (String menuId : roleRequest.getMenuIds()) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setId(IdWorker.nextId());
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(role.getId());
                sysRoleMenuDao.insert(sysRoleMenu);
            }
        }
        return ResultData.ok();
    }

    /**
     * 更新角色信息
     *
     * @param roleRequest .
     * @return .
     */
    @PatchMapping("roles/{id}")
    public ResultData updateRole(@RequestBody RoleRequest roleRequest, @PathVariable("id") String roleId) {
        SysRole sysRole = sysRoleDao.findById(roleId);
        if (sysRole==null) {
            return ResultData.notFound();
        }
        BeanUtils.copyProperties(roleRequest, sysRole, "id");
        sysRoleDao.update(sysRole);
        sysRoleMenuDao.deleteByRoleId(roleId);
        if (roleRequest.getMenuIds() != null) {
            for (String menuId : roleRequest.getMenuIds()) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setId(IdWorker.nextId());
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(sysRole.getId());
                sysRoleMenuDao.insert(sysRoleMenu);
            }
        }
        return ResultData.ok();
    }

    /**
     * 根据角色id删除角色
     *
     * @param id .
     * @return .
     */
    @DeleteMapping("roles/{id}")
    public ResultData deleteRole(@PathVariable("id") String id) {
        sysRoleDao.deleteById(id);
        sysRoleMenuDao.deleteByRoleId(id);
        sysUserRoleDao.deleteByRoleId(id);
        return ResultData.ok();
    }

}
