package com.touchspring.smartforecasting.dao;


import com.touchspring.smartforecasting.dao.base.BaseMapper;
import com.touchspring.smartforecasting.domain.entity.sys.SysRoleMenu;
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
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenu> {

    List<SysRoleMenu> findByRoleId(@Param("roleId") String roleId);

    List<SysRoleMenu> findByRoleIdIn(@Param("list") Iterable<String> roleIds);

    @Transactional
    void deleteByRoleId(@Param("roleId") String roleId);

    @Transactional
    void deleteByMenuId(@Param("menuId") String menuId);

    int insertALL(@Param("list") List<SysRoleMenu> list);

    int updateALL(@Param("list") List<SysRoleMenu> list);

}
