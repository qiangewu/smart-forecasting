package com.touchspring.smartforecasting.dao;


import com.touchspring.smartforecasting.dao.base.BaseMapper;
import com.touchspring.smartforecasting.domain.entity.sys.SysCodingRuleDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author byh
 */

@Mapper
@Component
public interface SysCodingRuleDetailDao extends BaseMapper<SysCodingRuleDetail> {

    List<SysCodingRuleDetail> findBySysCodingRuleIdOrderBySortAsc(@Param("sysCodingRuleId") String sysCodingRuleId);

    List<SysCodingRuleDetail> selectPage(@Param("direction") String direction);
}
