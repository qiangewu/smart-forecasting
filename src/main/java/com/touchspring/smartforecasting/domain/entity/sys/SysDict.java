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
@Table(name = "sys_dict")
@SQLDelete(sql = "update sys_dict  set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
public class SysDict extends BaseIdEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "is_active")
    private String isActive;

    @Column(name = "remark")
    private String remark;

    @OneToMany(targetEntity = SysDictData.class, mappedBy = "sysDict")
    private List<SysDictData> data;

}
