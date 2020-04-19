package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.core.utils.StringUtils;
import com.touchspring.smartforecasting.dao.SysRoleMenuDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysMenu;
import com.touchspring.smartforecasting.domain.entity.sys.SysRoleMenu;
import com.touchspring.smartforecasting.service.SysRoleMenuService;
import com.touchspring.smartforecasting.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RoleMenuController {

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    /**
     * 分页查询用户信息
     * @param currentPage
     * @param pageSize
     * @return
     */
    /*@GetMapping("userRole")
    public ResultData findAllUsers(int currentPage,int pageSize) {
        Page<SysUserRole> allUsers = sysUserRoleRepository.findAll(PageRequest.of(currentPage, pageSize,new Sort(Sort.Direction.DESC,"createAt")));
        long totalCount = allUsers.getTotalElements();
        List<SysUserRole> users = allUsers.getContent();
        return ResultData.ok().putDataValue("users", users).putDataValue("totalCount",totalCount);
    }*/


    /**
     * 根据roleId查询相应的菜单信息
     * @param roleId
     * @return
     */
    @GetMapping("roleMenu/{roleId}")
    public ResultData findByRoleId(@PathVariable("roleId") String roleId) {
        List<SysMenu> menus= new ArrayList<>();
        List<SysRoleMenu> roles = sysRoleMenuDao.findByRoleId(roleId);
        if ((null != roles) && (roles.size()>0 )){
            for (SysRoleMenu roleMenu: roles) {
                SysMenu menu = roleMenu.getMenu();
                menus.add(menu);
            }
        }
        return ResultData.ok().putDataValue("roleMenus", menus);
    }


   /**
     * 增加 编辑菜单角色关联表信息
     * @param menuIds
     * @return
     */
    @PostMapping("roleMenu/{roleId}")
    public ResultData insertUser(@PathVariable("roleId") String roleId,String menuIds) {

        sysRoleMenuDao.deleteByRoleId(roleId);
        List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
        if (!StringUtils.isEmpty(menuIds)){
            String[] split = menuIds.split(",");
            List<SysRoleMenu> roleMenus = new ArrayList<>();

            for (String menuId:split ) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setId(IdWorker.nextId());
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setMenuId(menuId);
                roleMenus.add(sysRoleMenu);
            }
            sysRoleMenus = sysRoleMenuService.insertAll(roleMenus);
        }
        return ResultData.ok().putDataValue("roleMenus", sysRoleMenus);
    }



    /**
     * 根据id删除关联表
     * @param id
     * @return
     */
    @DeleteMapping("roleMenu/{id}")
    public ResultData deleteUser(@PathVariable("id") String id) {
        sysRoleMenuDao.deleteById(id);
        return ResultData.ok();
    }

}
