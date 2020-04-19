package com.touchspring.smartforecasting.repository.sys;

import com.touchspring.smartforecasting.domain.entity.sys.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, String>, JpaSpecificationExecutor<SysUser>{

    Optional<SysUser> findByLoginName(String loginName);

    Optional<SysUser> findByUserName(String userName);

    Optional<SysUser> findByLoginNameAndPassword(String loginName, String password);

}
