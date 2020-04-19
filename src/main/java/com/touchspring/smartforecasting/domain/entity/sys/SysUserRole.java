package com.touchspring.smartforecasting.domain.entity.sys;


import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_user_role")
public class SysUserRole {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "role_id")
    private String roleId;

    @ManyToOne(targetEntity = SysUser.class)
    @JoinColumn(name = "user_id",  insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    private SysUser user;

    @ManyToOne(targetEntity = SysRole.class)
    @JoinColumn(name = "role_id",  insertable = false, updatable = false)
    @NotFound(action= NotFoundAction.IGNORE)
    private SysRole role;
}
