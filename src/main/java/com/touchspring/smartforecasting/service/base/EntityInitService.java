package com.touchspring.smartforecasting.service.base;

import com.touchspring.core.enums.SimpleStatus;
import com.touchspring.smartforecasting.domain.entity.base.BaseIdEntity;
import com.touchspring.smartforecasting.domain.entity.sys.SysRoleMenu;
import com.touchspring.smartforecasting.utils.IdWorker;

import java.time.LocalDateTime;

/**
 * 转换数据库插入和更新
 * @param <E>
 */
public class EntityInitService<E extends BaseIdEntity> {

    protected void preUpdate(E e){
        e.setUpdateAt(LocalDateTime.now());
    }

    protected void preInsert(E e) {
        e.setId(IdWorker.nextId());
        e.setIsDeleted(SimpleStatus.NO.getCode());
        e.setCreateAt(LocalDateTime.now());
        e.setUpdateAt(LocalDateTime.now());
    }

}
