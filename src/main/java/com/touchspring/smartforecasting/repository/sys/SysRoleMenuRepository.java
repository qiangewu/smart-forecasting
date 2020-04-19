package com.touchspring.smartforecasting.repository.sys;

import com.touchspring.smartforecasting.domain.entity.sys.SysRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SysRoleMenuRepository extends JpaRepository<SysRoleMenu, String>, JpaSpecificationExecutor<SysRoleMenu> {

    List<SysRoleMenu> findByRoleId(String roleId);

    List<SysRoleMenu> findByRoleIdIn(Iterable<String> roleIds);

    @Transactional
    void deleteByRoleId(String roleId);

    @Transactional
    void deleteByMenuId(String menuId);

}
