package com.touchspring.smartforecasting.domain.entity.sys;


import com.touchspring.smartforecasting.domain.entity.base.BaseIdEntity;
import com.touchspring.smartforecasting.domain.enums.PrivilegeEnums;
import com.touchspring.smartforecasting.domain.enums.SimpleEnums;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "sys_menu")
@SQLDelete(sql = "update `sys_menu` set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
public class SysMenu extends BaseIdEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "application_id")
    private String applicationId;

    @Column(name = "parent_id")
    private String parentId;

    @Column(name = "path")
    private String path;

    @Column(name = "icon")
    private String icon;

    @Column(name = "code")
    private String code;



    @OneToMany(targetEntity = SysMenu.class)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private List<SysMenu> children;

    @Column(name = "sort")
    private String sort;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_active")
    private SimpleEnums isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PrivilegeEnums type;
}
