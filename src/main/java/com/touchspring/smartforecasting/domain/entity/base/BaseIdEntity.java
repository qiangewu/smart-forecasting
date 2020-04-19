package com.touchspring.smartforecasting.domain.entity.base;

import com.touchspring.core.enums.SimpleStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 实体类基类
 *
 * @author wangfei
 */
@Data
@MappedSuperclass
public abstract class BaseIdEntity {


    /**
     * Id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 是否删除
     */
    @Column(name = "IS_DELETED")
    private Integer isDeleted;

    /**
     * 创建时间
     */
    @Column(name = "create_at")
    private LocalDateTime createAt;


    /**
     * 更新时间
     */
    @Column(name = "update_at")
    private LocalDateTime updateAt;



    @PreUpdate
    public void preUpdate() {
        this.setUpdateAt(LocalDateTime.now());
    }

    @PrePersist
    public void prePersist() {
        this.setIsDeleted(SimpleStatus.NO.getCode());
        this.setCreateAt(LocalDateTime.now());
        this.setUpdateAt(LocalDateTime.now());
    }

//    @ManyToOne(targetEntity = SysUser.class)
//    @JoinColumn(name = "create_user_id", insertable = false, updatable = false)
//    @NotFound(action = NotFoundAction.IGNORE)
//    private SysUser createUser;
//
//
//    @ManyToOne(targetEntity = SysUser.class)
//    @JoinColumn(name = "update_user_id", insertable = false, updatable = false)
//    @NotFound(action = NotFoundAction.IGNORE)
//    private SysUser updateUser;

}