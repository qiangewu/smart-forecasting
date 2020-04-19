package com.touchspring.smartforecasting.repository.sys;

import com.touchspring.smartforecasting.domain.entity.sys.SysLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@Repository
public interface SysLogRepository extends JpaRepository<SysLog, String>, JpaSpecificationExecutor<SysLog> {

    Page<SysLog> findByCreateAtBetween(LocalDateTime startAt, LocalDateTime endAt, Pageable pageable);
}
