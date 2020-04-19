package com.touchspring.smartforecasting.service;

import com.touchspring.smartforecasting.dao.SysRoleMenuDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysRoleMenu;
import com.touchspring.smartforecasting.service.base.BaseService;
import com.touchspring.smartforecasting.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SysRoleMenuService implements BaseService<SysRoleMenu> {
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    public int save(SysRoleMenu sysRoleMenu) {
        int num;
        if(sysRoleMenu.getId() == null){
            sysRoleMenu.setId(IdWorker.nextId());
            num = sysRoleMenuDao.insert(sysRoleMenu);
        }else {
            num = sysRoleMenuDao.update(sysRoleMenu);
        }
        return num;
    }

    public List<SysRoleMenu> insertAll(List<SysRoleMenu> roleMenus) {
        sysRoleMenuDao.insertALL(roleMenus);
        return roleMenus;
    }
}
