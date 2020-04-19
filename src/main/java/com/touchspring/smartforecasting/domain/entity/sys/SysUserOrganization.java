package com.touchspring.smartforecasting.domain.entity.sys;

import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_user_organization")
public class SysUserOrganization {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "sys_user_id")
    private String sysUserId;

    @Column(name = "organization_id")
    private String organizationId;

    @ManyToOne(targetEntity = SysUser.class)
    @JoinColumn(name = "sys_user_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysUser user;

    @ManyToOne(targetEntity = SysOrganization.class)
    @JoinColumn(name = "organization_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysOrganization organization;


}
