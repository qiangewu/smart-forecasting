package com.touchspring.smartforecasting.dao;


import com.touchspring.smartforecasting.dao.base.BaseMapper;
import com.touchspring.smartforecasting.domain.entity.sys.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author byh
 */

@Mapper
@Component
public interface SysMenuDao extends BaseMapper<SysMenu> {
    List<SysMenu> findByName(@Param("name") String name);

    List<SysMenu> findByPath(@Param("path") String path);

    List<SysMenu> findByParentId(@Param("parentId") String parentId);

    List<SysMenu> findAllByDirection(@Param("direction") String direction);
}
