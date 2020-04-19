package com.touchspring.smartforecasting.dao;


import com.touchspring.smartforecasting.dao.base.BaseMapper;
import com.touchspring.smartforecasting.domain.entity.sys.SysLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author byh
 */

@Mapper
@Component
public interface SysLogDao extends BaseMapper<SysLog> {

    List<SysLog> searchByCondition(@Param("startAt") LocalDateTime startAt, @Param("endAt") LocalDateTime endAt,
                                   @Param("event") String event,
                                   @Param("direction") String direction);

}
