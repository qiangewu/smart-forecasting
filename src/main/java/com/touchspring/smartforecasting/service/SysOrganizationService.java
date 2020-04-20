package com.touchspring.smartforecasting.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touchspring.core.utils.StringUtils;
import com.touchspring.smartforecasting.dao.SysOrganizationDao;
import com.touchspring.smartforecasting.dao.SysUserOrganizationDao;
import com.touchspring.smartforecasting.domain.entity.base.BaseIdEntity;
import com.touchspring.smartforecasting.domain.entity.sys.SysOrganization;
import com.touchspring.smartforecasting.domain.entity.sys.SysUserOrganization;
import com.touchspring.smartforecasting.service.base.BaseService;
import com.touchspring.smartforecasting.service.base.PageHelperService;
import com.touchspring.smartforecasting.utils.IdWorker;
import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;
import com.touchspring.smartforecasting.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysOrganizationService implements BaseService<SysOrganization>, PageHelperService {

    @Autowired
    private SysOrganizationDao sysOrganizationDao;
    @Autowired
    private SysUserOrganizationDao sysUserOrganizationDao;


    /**
     * 查找 相关树节点
     *
     * @param sysOrganizations .
     * @param sysOrganization  .
     * @return .
     */
    public List<SysOrganization> findChildren(List<SysOrganization> sysOrganizations, SysOrganization sysOrganization) {

        List<SysOrganization> children = new ArrayList<>();


        List<SysOrganization> leaf = sysOrganizations.stream().filter(x -> StringUtils.equals(x.getParentId(), sysOrganization.getId())).collect(Collectors.toList());

        leaf.forEach(x -> {
            children.addAll(findChildren(sysOrganizations, x));
        });

        children.add(sysOrganization);

        return children;

    }

    public List<String> findChildrenIdsById(String organizationId) {
        List<String> targetOrganizationIds;
        if (StringUtils.isNotBlank(organizationId)) {
            SysOrganization organization = sysOrganizationDao.findById(organizationId);
            if (organization!=null) {
                List<SysOrganization> sysOrganizations = sysOrganizationDao.findAll();
                targetOrganizationIds = this.findChildren(sysOrganizations, organization).stream().map(BaseIdEntity::getId).collect(Collectors.toList());
            } else {
                targetOrganizationIds = new ArrayList<>();
            }
        } else {
            targetOrganizationIds = new ArrayList<>();
        }
        return targetOrganizationIds;
    }

    public List<SysOrganization> findByUserId(String userId) {
        List<SysUserOrganization> sysUserOrganizations = sysUserOrganizationDao.findBySysUserId(userId);
        return sysUserOrganizations.stream().map(SysUserOrganization::getOrganization).collect(Collectors.toList());
    }

    @Override
    public int save(SysOrganization sysOrganization) {
        int num;
        if(sysOrganization.getId() == null){
            sysOrganization.setId(IdWorker.nextId());
            num = sysOrganizationDao.insert(sysOrganization);
        }else {
            num = sysOrganizationDao.update(sysOrganization);
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
    private PageInfo<SysOrganization> getPageInfo(PageRequestOfMybatis pageRequestOfMybatis, String direction) {
        int pageNum = pageRequestOfMybatis.getPageNum();
        int pageSize = pageRequestOfMybatis.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SysOrganization> sysOrganizations = sysOrganizationDao.selectPage(direction);
        return new PageInfo<SysOrganization>(sysOrganizations);
    }
}
