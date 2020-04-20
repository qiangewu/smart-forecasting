package com.touchspring.smartforecasting.dao;


import com.touchspring.smartforecasting.dao.base.BaseMapper;
import com.touchspring.smartforecasting.domain.entity.sys.SysI18NDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author byh
 */

@Mapper
@Component
public interface SysI18NDetailDao extends BaseMapper<SysI18NDetail>{
    List<SysI18NDetail> findByI18nCode(@Param("i18n_code") String i18nCode);

    void deleteByI18nCode(@Param("i18nCode") String i18nCode);
}
