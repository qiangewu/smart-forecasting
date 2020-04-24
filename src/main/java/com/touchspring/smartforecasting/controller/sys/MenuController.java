package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.core.utils.StringUtils;
import com.touchspring.smartforecasting.dao.SysMenuDao;
import com.touchspring.smartforecasting.dao.SysRoleMenuDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysMenu;
import com.touchspring.smartforecasting.service.SysMenuService;
import com.touchspring.smartforecasting.service.SysRoleMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
public class MenuController {
    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 查询菜单信息
     *
     * @return .
     */
    @GetMapping("menus")
    public ResultData findAllMenus(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "sort") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) {
        if (StringUtils.equals("ASC", direction.toUpperCase())) {
            direction = "ASC";
        } else {
            direction = "DESC";
        }
        List<SysMenu> menus = sysMenuDao.findAllByDirection(direction);
        return ResultData.ok().putDataValue("menus", menus);
    }

    /**
     * 根据菜单id查询菜单信息
     *
     * @param id .
     * @return .
     */
    @GetMapping("menus/{id}")
    public ResultData findMenuById(@PathVariable("id") String id) {
        SysMenu menu = sysMenuDao.findById(id);
        if (menu==null) {
            return ResultData.notFound();
        }
        return ResultData.ok().putDataValue("menu", menu);
    }


    /**
     * 增加菜单信息
     *
     * @param sysMenu .
     * @return .
     */
    @PostMapping("menus")
    public ResultData insertMenu(@RequestBody SysMenu sysMenu) {
        List<SysMenu> path = sysMenuDao.findByPath(sysMenu.getPath());
        if (path!=null&&path.size()>0) {
            return new ResultData(1400, "前端路径重复，请重新输入！");
        }
        sysMenuService.save(sysMenu);
        return ResultData.ok().putDataValue("menu", sysMenu);
    }

    /**
     * 更新菜单信息
     *
     * @param sysMenu .
     * @return .
     */
    @PatchMapping("menus/{id}")
    public ResultData updateMenu(@RequestBody SysMenu sysMenu, @PathVariable("id") String id) {
        SysMenu target = sysMenuDao.findById(id);
        if (target==null) {
            return ResultData.notFound();
        }
        BeanUtils.copyProperties(sysMenu, target, "id", "createAt", "updateAt", "createUserId", "updateUserId", "isDeleted", "sort");
        sysMenuService.save(target);
        return ResultData.ok();
    }

    /**
     * 菜单上移
     *
     * @param id .
     * @return .
     */
    @PatchMapping("menus/{id}/up")
    public ResultData upMenu(@PathVariable("id") String id) {
        SysMenu target = sysMenuDao.findById(id);
        if (target==null) {
            return ResultData.notFound();
        }
        List<SysMenu> menus = sysMenuDao.findByParentId(target.getParentId());

        menus.sort((a, b) -> -a.getSort().compareTo(b.getSort()));
        Optional<SysMenu> lessOptional = menus.stream().filter(x -> x.getSort().compareTo(target.getSort()) < 0).findFirst();

        if (!lessOptional.isPresent()) {
            ResultData resultData = ResultData.errorRequest();
            resultData.setMessage("菜单无法上移");
            return resultData;
        }
        SysMenu less = lessOptional.get();
        swapMenuSort(target, less);
        return ResultData.ok();
    }


    /**
     * 菜单下移
     *
     * @param id .
     * @return .
     */
    @PatchMapping("menus/{id}/down")
    public ResultData downMenu(@PathVariable("id") String id) {
        SysMenu target = sysMenuDao.findById(id);
        if (target == null) {
            return ResultData.notFound();
        }
        List<SysMenu> menus = sysMenuDao.findByParentId(target.getParentId());
        menus.sort(Comparator.comparing(SysMenu::getSort));
        Optional<SysMenu> greaterOptional = menus.stream().filter(x -> x.getSort().compareTo(target.getSort()) > 0).findFirst();
        if (!greaterOptional.isPresent()) {
            ResultData resultData = ResultData.errorRequest();
            resultData.setMessage("菜单无法下移");
            return resultData;
        }
        SysMenu greater = greaterOptional.get();
        swapMenuSort(target, greater);
        return ResultData.ok();
    }

    private void swapMenuSort(SysMenu a, SysMenu b) {
        String tempSort = b.getSort();
        b.setSort(a.getSort());
        a.setSort(tempSort);
        sysMenuDao.update(b);
        sysMenuDao.update(a);
    }


    /**
     * 根据菜单id删除菜单
     *
     * @param id .
     * @return .
     */
    @DeleteMapping("menus/{id}")
    public ResultData deleteMenu(@PathVariable("id") String id) {
        sysRoleMenuDao.deleteByMenuId(id);
        sysMenuDao.deleteById(id);
        return ResultData.ok();
    }

}
