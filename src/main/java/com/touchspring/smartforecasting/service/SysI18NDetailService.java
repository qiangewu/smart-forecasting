package com.touchspring.smartforecasting.service;

import com.touchspring.smartforecasting.dao.SysI18NDetailDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysI18NDetail;
import com.touchspring.smartforecasting.service.base.BaseService;
import com.touchspring.smartforecasting.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SysI18NDetailService implements BaseService<SysI18NDetail> {
    @Autowired
    private SysI18NDetailDao sysI18NDetailDao;

    @Override
    public int save(SysI18NDetail sysI18NDetail) {
        int num;
        if(sysI18NDetail.getId()==null){
            sysI18NDetail.setId(IdWorker.nextId());
            num = sysI18NDetailDao.insert(sysI18NDetail);
        }else {
            num = sysI18NDetailDao.update(sysI18NDetail);
        }
        return num;
    }


}
