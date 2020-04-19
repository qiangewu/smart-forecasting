package com.touchspring.smartforecasting.service.base;


import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;

public interface PageHelperService {
    /**
     * 分页查询接口
     * @param pageRequestOfMybatis 自定义，统一分页查询请求
     * @return PageResult 自定义，统一分页查询结果
     */
    PageResult findPage(PageRequestOfMybatis pageRequestOfMybatis, String direction);


}
