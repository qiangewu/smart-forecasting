package com.touchspring.smartforecasting.domain.entity.sys;

import com.touchspring.smartforecasting.domain.entity.base.BaseIdEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_coding_rule")
@SQLDelete(sql = "update sys_coding_rule  set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
public class SysCodingRule extends BaseIdEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "review")
    private String review;

    @OneToMany(targetEntity = SysCodingRuleDetail.class,mappedBy = "sysCodingRule")
    private List<SysCodingRuleDetail> details;

}
