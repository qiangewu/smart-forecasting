package com.touchspring.smartforecasting.domain.entity.sys;

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
@Table(name = "sys_organization")
@SQLDelete(sql = "update sys_organization  set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
public class SysOrganization extends BaseIdEntity {

    @Column(name = "name")
    private String name;


    @ManyToOne(targetEntity = SysUser.class)
    @JoinColumn(name = "create_user_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysUser createUser;

    @ManyToOne(targetEntity = SysUser.class)
    @JoinColumn(name = "update_user_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysUser updateUser;

    @Column(name = "parent_id")
    private String parentId;

    private String code;

    private String path;


}
