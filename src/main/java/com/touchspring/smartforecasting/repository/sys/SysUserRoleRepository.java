package com.touchspring.smartforecasting.repository.sys;

import com.touchspring.smartforecasting.domain.entity.sys.SysUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SysUserRoleRepository extends JpaRepository<SysUserRole, String>, JpaSpecificationExecutor<SysUserRole> {

    List<SysUserRole> findByUserId(String userId);

    @Transactional
    void  deleteByUserId(String userId);

    @Transactional
    void  deleteByRoleId(String roleId);
}
