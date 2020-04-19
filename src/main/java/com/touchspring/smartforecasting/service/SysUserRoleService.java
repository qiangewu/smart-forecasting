package com.touchspring.smartforecasting.service;

import com.touchspring.smartforecasting.dao.SysUserRoleDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysUserRole;
import com.touchspring.smartforecasting.service.base.BaseService;
import com.touchspring.smartforecasting.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SysUserRoleService implements BaseService<SysUserRole> {
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public int save(SysUserRole sysUserRole) {
        int num;
        if(sysUserRole.getId() == null){
            sysUserRole.setId(IdWorker.nextId());
            num = sysUserRoleDao.insert(sysUserRole);
        }else {
            num = sysUserRoleDao.update(sysUserRole);
        }
        return num;
    }
}
