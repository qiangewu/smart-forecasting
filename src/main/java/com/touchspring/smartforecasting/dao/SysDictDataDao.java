package com.touchspring.smartforecasting.dao;


import com.touchspring.smartforecasting.dao.base.BaseMapper;
import com.touchspring.smartforecasting.domain.entity.sys.SysDictData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author byh
 */

@Mapper
@Component
public interface SysDictDataDao extends BaseMapper<SysDictData> {

    List<SysDictData> findBySysDictIdOrderBySortAsc(@Param("sysDictId") String sysDictId);



    List<SysDictData> selectPage(@Param("direction") String direction);
}
