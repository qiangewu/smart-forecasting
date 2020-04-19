package com.touchspring.smartforecasting.domain.entity.sys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.touchspring.smartforecasting.domain.entity.base.BaseIdEntity;
import com.touchspring.smartforecasting.domain.enums.CodingRuleCycleEnums;
import com.touchspring.smartforecasting.domain.enums.CodingRuleEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_coding_rule_detail")
@SQLDelete(sql = "update sys_coding_rule_detail  set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
public class SysCodingRuleDetail extends BaseIdEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CodingRuleEnums type;

    @Column(name = "node")
    private String node;

    @Column(name = "review")
    private String review;

    @Column(name = "sys_coding_rule_id")
    private String sysCodingRuleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "cycle")
    private CodingRuleCycleEnums cycle;

    @Column(name = "current_value")
    private String currentValue;

    @Column(name = "step")
    private String step;

    @Column(name = "length")
    private Long length;

    @Column(name = "sort")
    private String sort;

    @JsonIgnore
    @ManyToOne(targetEntity = SysCodingRule.class)
    @JoinColumn(name = "sys_coding_rule_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysCodingRule sysCodingRule;

}
