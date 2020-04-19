package com.touchspring.smartforecasting.repository.sys;

import com.touchspring.smartforecasting.domain.entity.sys.SysDict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SysDictRepository extends JpaRepository<SysDict, String>, JpaSpecificationExecutor<SysDict> {

}
