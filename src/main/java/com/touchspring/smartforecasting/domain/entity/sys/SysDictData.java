package com.touchspring.smartforecasting.domain.entity.sys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.touchspring.smartforecasting.domain.entity.base.BaseIdEntity;
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
@Table(name = "sys_dict_data")
@SQLDelete(sql = "update sys_dict_data  set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
public class SysDictData extends BaseIdEntity {

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;


    @Column(name = "sys_dict_id")
    private String sysDictId;


    @Column(name = "detail")
    private String detail;

    @Column(name = "sort")
    private String sort;

    @JsonIgnore
    @ManyToOne(targetEntity = SysDict.class)
    @JoinColumn(name = "sys_dict_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysDict sysDict;


}
