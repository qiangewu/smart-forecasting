package com.touchspring.smartforecasting.repository.sys;

import com.touchspring.smartforecasting.domain.entity.sys.SysCodingRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysCodingRuleRepository extends JpaRepository<SysCodingRule, String>, JpaSpecificationExecutor<SysCodingRule> {

    Optional<SysCodingRule> findByCode(String code);
}
