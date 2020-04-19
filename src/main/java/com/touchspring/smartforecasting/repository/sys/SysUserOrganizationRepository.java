package com.touchspring.smartforecasting.repository.sys;

import com.touchspring.smartforecasting.domain.entity.sys.SysUserOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SysUserOrganizationRepository extends JpaRepository<SysUserOrganization, String>, JpaSpecificationExecutor<SysUserOrganization> {

    List<SysUserOrganization>findBySysUserId(String sysUserId);

    @Transactional
    void  deleteBySysUserId(String sysUserId);

    @Transactional
    void  deleteByOrganizationId(String organizationId);

}
