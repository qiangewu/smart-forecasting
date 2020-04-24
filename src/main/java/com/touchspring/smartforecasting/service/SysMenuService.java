package com.touchspring.smartforecasting.service;

import com.touchspring.smartforecasting.dao.SysMenuDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysMenu;
import com.touchspring.smartforecasting.service.base.BaseService;
import com.touchspring.smartforecasting.service.base.EntityInitService;
import com.touchspring.smartforecasting.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SysMenuService extends EntityInitService<SysMenu> implements BaseService<SysMenu>  {

    @Autowired
    private SysMenuDao sysMenuDao;

    @Override
    public int save(SysMenu sysMenu) {
        int num;
        if(sysMenu.getId() == null){
            preInsert(sysMenu);
            sysMenu.setSort(sysMenu.getId());
            num = sysMenuDao.insert(sysMenu);
        }else {
            preUpdate(sysMenu);
            num = sysMenuDao.update(sysMenu);
        }
        return num;
    }
}
