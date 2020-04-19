package com.touchspring.smartforecasting.dao;


import com.touchspring.smartforecasting.dao.base.BaseMapper;
import com.touchspring.smartforecasting.domain.entity.sys.SysUserOrganization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * @author byh
 */

@Mapper
@Component
public interface SysUserOrganizationDao extends BaseMapper<SysUserOrganization> {
    List<SysUserOrganization> findBySysUserId(@Param("sysUserId") String sysUserId);

    @Transactional
    void  deleteBySysUserId(@Param("sysUserId") String sysUserId);

    @Transactional
    void  deleteByOrganizationId(@Param("organizationId") String organizationId);

    List<SysUserOrganization> selectPage(@Param("direction") String direction);
}
