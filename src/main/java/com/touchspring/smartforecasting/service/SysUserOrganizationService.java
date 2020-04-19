package com.touchspring.smartforecasting.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touchspring.smartforecasting.dao.SysUserOrganizationDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysUserOrganization;
import com.touchspring.smartforecasting.service.base.BaseService;
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
public class SysUserOrganizationService implements BaseService<SysUserOrganization>, PageHelperService {
    @Autowired
    private SysUserOrganizationDao sysUserOrganizationDao;

    @Override
    public int save(SysUserOrganization sysUserOrganization) {
        int num;
        if(sysUserOrganization.getId() == null){
            sysUserOrganization.setId(IdWorker.nextId());
            num = sysUserOrganizationDao.insert(sysUserOrganization);
        }else {
            num = sysUserOrganizationDao.update(sysUserOrganization);
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
    private PageInfo<SysUserOrganization> getPageInfo(PageRequestOfMybatis pageRequestOfMybatis, String direction) {
        int pageNum = pageRequestOfMybatis.getPageNum();
        int pageSize = pageRequestOfMybatis.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SysUserOrganization> userOrganizationList = sysUserOrganizationDao.selectPage(direction);
        return new PageInfo<SysUserOrganization>(userOrganizationList);
    }
}
