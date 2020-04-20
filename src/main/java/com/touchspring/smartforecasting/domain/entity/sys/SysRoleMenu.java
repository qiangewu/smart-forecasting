package com.touchspring.smartforecasting.domain.entity.sys;


import com.touchspring.smartforecasting.domain.entity.base.BaseIdEntity;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_role_menu")
public class SysRoleMenu extends BaseIdEntity{

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "menu_id")
    private String menuId;

    @Column(name = "role_id")
    private String roleId;

    @ManyToOne(targetEntity = SysMenu.class)
    @JoinColumn(name = "menu_id",  insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    private SysMenu menu;

    @ManyToOne(targetEntity = SysRole.class)
    @JoinColumn(name = "role_id",  insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    private SysRole role;
}
