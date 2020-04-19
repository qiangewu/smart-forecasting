package com.touchspring.smartforecasting.dao;

import com.touchspring.smartforecasting.dao.base.BaseMapper;
import com.touchspring.smartforecasting.domain.entity.sys.SysCodingRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SysCodingRuleDao extends BaseMapper<SysCodingRule> {
    List<SysCodingRule> findByCode(@Param("code") String code);


    List<SysCodingRule> selectPage(@Param("direction") String direction);
}
