package com.touchspring.smartforecasting.repository.sys;

import com.touchspring.smartforecasting.domain.entity.sys.SysDictData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysDictDataRepository extends JpaRepository<SysDictData, String>, JpaSpecificationExecutor<SysDictData> {

    List<SysDictData> findBySysDictIdOrderBySortAsc(String sysDictId);

}
