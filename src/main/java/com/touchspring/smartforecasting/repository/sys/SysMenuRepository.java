package com.touchspring.smartforecasting.repository.sys;

import com.touchspring.smartforecasting.domain.entity.sys.SysMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysMenuRepository extends JpaRepository<SysMenu, String>, JpaSpecificationExecutor<SysMenu> {

    Optional<SysMenu> findByName(String name);

    Optional<SysMenu> findByPath(String path);

    List<SysMenu> findByParentId(String parentId);

}
