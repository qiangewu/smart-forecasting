package com.touchspring.smartforecasting.dao.base;

import java.util.List;

/**
 * Created by wangfei on 2016/7/1.
 */
public interface BaseMapper<E> {
//    List<E> findAll();
//
    E find(E e);

    int insert(E e);

    int update(E e);

    int delete(String id);

//    int deleteById(String id);

//    E findById(String id);

}
