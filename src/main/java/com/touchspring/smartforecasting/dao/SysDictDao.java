package com.touchspring.smartforecasting.dao;


import com.touchspring.smartforecasting.dao.base.BaseMapper;
import com.touchspring.smartforecasting.domain.entity.sys.SysDict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author byh
 */

@Mapper
@Component
public interface SysDictDao extends BaseMapper<SysDict> {


    List<SysDict> selectPage(@Param("direction") String direction);
    List<SysDict> findByCode(@Param("code") String code);
}
