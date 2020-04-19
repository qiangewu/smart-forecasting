package com.touchspring.smartforecasting.domain.entity.sys;


import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sys_log")
public class SysLog {

    /**
     * Id
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 用户id
     */
    @Column(name = "create_user_id")
    private String createUserId;


    /**
     * 事件
     */
    @Column(name = "event")
    private String event;

    /**
     * 事件
     */
    @Column(name = "object_id")
    private String objectId;


    /**
     * 操作动作的描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 请求数据
     */
    @Column(name = "request_data")
    private String requestData;

    /**
     * 操作的url
     */
    @Column(name = "url")
    private String url;

    /**
     * 创建时间
     */
    @Column(name = "create_at")
    private LocalDateTime createAt;


    @PrePersist
    public void prePersist() {
        this.setCreateAt(LocalDateTime.now());
    }

    @ManyToOne(targetEntity = SysUser.class)
    @JoinColumn(name = "create_user_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private SysUser createUser;

}
