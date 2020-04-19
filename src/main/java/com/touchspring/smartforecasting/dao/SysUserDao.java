package com.touchspring.smartforecasting.dao;


import com.touchspring.smartforecasting.dao.base.BaseMapper;
import com.touchspring.smartforecasting.domain.entity.sys.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author byh
 */

@Mapper
@Component
public interface SysUserDao extends BaseMapper<SysUser> {

    List<SysUser> findByLoginName(@Param("loginName") String loginName);

    List<SysUser> findByUserName(@Param("userName") String userName);

    List<SysUser> findByLoginNameAndPassword(@Param("loginName") String loginName, @Param("password") String password);

    List<SysUser> selectPage(@Param("direction") String direction);
}
