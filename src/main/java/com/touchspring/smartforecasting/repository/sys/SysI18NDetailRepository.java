package com.touchspring.smartforecasting.repository.sys;

import com.touchspring.smartforecasting.domain.entity.sys.SysI18NDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SysI18NDetailRepository extends JpaRepository<SysI18NDetail, String>, JpaSpecificationExecutor<SysI18NDetail> {

    List<SysI18NDetail> findByI18nCode(String i18nCode);

    @Transactional
    void deleteByI18nCode(String i18nCode);
}
