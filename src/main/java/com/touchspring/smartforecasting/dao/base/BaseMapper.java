package com.touchspring.smartforecasting.dao.base;

import com.touchspring.smartforecasting.domain.entity.sys.SysRole;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * Created by wangfei on 2016/7/1.
 */
public interface BaseMapper<E> {

    List<E> findAll();

    int deleteById(String id);

    E findById(String id);

    E find(E e);

    int insert(E e);

    int update(E e);

    int delete(String id);

}
