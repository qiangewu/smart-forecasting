package com.touchspring.smartforecasting.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touchspring.smartforecasting.dao.SysRoleDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysRole;
import com.touchspring.smartforecasting.service.base.BaseService;
import com.touchspring.smartforecasting.service.base.EntityInitService;
import com.touchspring.smartforecasting.service.base.PageHelperService;
import com.touchspring.smartforecasting.utils.IdWorker;
import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;
import com.touchspring.smartforecasting.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SysRoleService extends EntityInitService<SysRole> implements BaseService<SysRole>, PageHelperService{
    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    public int save(SysRole sysRole) {
        int num;
        if(sysRole.getId() == null){
            preInsert(sysRole);
            num = sysRoleDao.insert(sysRole);
        }else {
            preUpdate(sysRole);
            num = sysRoleDao.update(sysRole);
        }
        return num;
    }

    @Override
    public PageResult findPage(PageRequestOfMybatis pageRequestOfMybatis, String direction) {
        return PageUtils.getPageResult(pageRequestOfMybatis, getPageInfo(pageRequestOfMybatis,direction));
    }

    /**
     * 调用分页插件完成分页
     * @param pageRequestOfMybatis
     * @return
     */
    private PageInfo<SysRole> getPageInfo(PageRequestOfMybatis pageRequestOfMybatis, String direction) {
        int pageNum = pageRequestOfMybatis.getPageNum();
        int pageSize = pageRequestOfMybatis.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRole> sysRoles = sysRoleDao.selectPage(direction);
        return new PageInfo<SysRole>(sysRoles);
    }

}
