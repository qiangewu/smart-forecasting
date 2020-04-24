package com.touchspring.smartforecasting.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touchspring.smartforecasting.dao.SysDictDataDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysDictData;
import com.touchspring.smartforecasting.service.base.BaseService;
import com.touchspring.smartforecasting.service.base.EntityInitService;
import com.touchspring.smartforecasting.service.base.PageHelperService;
import com.touchspring.smartforecasting.utils.IdWorker;
import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;
import com.touchspring.smartforecasting.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SysDictDateService extends EntityInitService<SysDictData> implements PageHelperService, BaseService<SysDictData> {
    @Autowired
    private SysDictDataDao sysDictDataDao;


    @Override
    public PageResult findPage(PageRequestOfMybatis pageRequestOfMybatis, String direction) {
        return PageUtils.getPageResult(pageRequestOfMybatis, getPageInfo(pageRequestOfMybatis,direction));
    }

    /**
     * 调用分页插件完成分页
     * @param pageRequestOfMybatis
     * @return
     */
    private PageInfo<SysDictData> getPageInfo(PageRequestOfMybatis pageRequestOfMybatis,String direction) {
        int pageNum = pageRequestOfMybatis.getPageNum();
        int pageSize = pageRequestOfMybatis.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SysDictData> sysDictData = sysDictDataDao.selectPage(direction);
        return new PageInfo<SysDictData>(sysDictData);
    }


    @Override
    public int save(SysDictData sysDictData) {
        int num;
        if(sysDictData.getId()==null){
            preInsert(sysDictData);
            num = sysDictDataDao.insert(sysDictData);
        }else {
            preUpdate(sysDictData);
            num = sysDictDataDao.update(sysDictData);
        }
        return num;
    }
}
