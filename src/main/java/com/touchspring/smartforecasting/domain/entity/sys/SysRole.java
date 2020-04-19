package com.touchspring.smartforecasting.domain.entity.sys;


import com.touchspring.smartforecasting.domain.entity.base.BaseIdEntity;
import com.touchspring.smartforecasting.domain.enums.SimpleEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_role")
@SQLDelete(sql = "update `sys_role` set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
public class SysRole extends BaseIdEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_active")
    private SimpleEnums isActive;

    @Column(name = "create_user_id")
    private String createUserId;

    @Column(name = "update_user_id")
    private String updateUserId;

}
