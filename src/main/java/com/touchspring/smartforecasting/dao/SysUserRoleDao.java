package com.touchspring.smartforecasting.dao;


import com.touchspring.smartforecasting.dao.base.BaseMapper;
import com.touchspring.smartforecasting.domain.entity.sys.SysUserRole;
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
public interface SysUserRoleDao extends BaseMapper<SysUserRole> {


    List<SysUserRole> findByUserId(@Param("userId") String userId);

    @Transactional
    void  deleteByUserId(@Param("userId") String userId);

    @Transactional
    void  deleteByRoleId(@Param("roleId") String roleId);

    void insertAll(@Param("list") List<SysUserRole> sysUserRoles);
}
