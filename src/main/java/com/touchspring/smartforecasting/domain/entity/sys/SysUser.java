package com.touchspring.smartforecasting.domain.entity.sys;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "sys_user")
@SQLDelete(sql = "update `sys_user` set is_deleted = 1 where id = ?")
@Where(clause = "is_deleted = 0")
public class SysUser extends BaseIdEntity {

    @Column(name = "login_name")
    private String loginName;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "user_name")
    private String userName;


    @Column(name = "sex")
    private String sex;

    @Column(name = "email")
    private String email;


    @Column(name = "user_no")
    private String userNo;

    @Column(name = "language")
    private String language;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_active")
    private SimpleEnums isActive;

}
