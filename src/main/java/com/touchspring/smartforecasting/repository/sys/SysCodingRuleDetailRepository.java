package com.touchspring.smartforecasting.repository.sys;

import com.touchspring.smartforecasting.domain.entity.sys.SysCodingRuleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysCodingRuleDetailRepository extends JpaRepository<SysCodingRuleDetail, String>, JpaSpecificationExecutor<SysCodingRuleDetail> {

    List<SysCodingRuleDetail> findBySysCodingRuleIdOrderBySortAsc(String sysCodingRuleId);


}
