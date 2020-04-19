package com.touchspring.smartforecasting.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touchspring.smartforecasting.dao.SysDictDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysDict;
import com.touchspring.smartforecasting.service.base.BaseService;
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
public class SysDictService implements PageHelperService, BaseService<SysDict> {
    @Autowired
    private SysDictDao sysDictDao;


    @Override
    public PageResult findPage(PageRequestOfMybatis pageRequestOfMybatis, String direction) {
        return PageUtils.getPageResult(pageRequestOfMybatis, getPageInfo(pageRequestOfMybatis,direction));
    }

    /**
     * 调用分页插件完成分页
     * @param pageRequestOfMybatis
     * @return
     */
    private PageInfo<SysDict> getPageInfo(PageRequestOfMybatis pageRequestOfMybatis, String direction) {
        int pageNum = pageRequestOfMybatis.getPageNum();
        int pageSize = pageRequestOfMybatis.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SysDict> sysDict = sysDictDao.selectPage(direction);
        return new PageInfo<SysDict>(sysDict);
    }

    @Override
    public int save(SysDict sysDict) {
        int num;
        if(sysDict.getId() == null){
            sysDict.setId(IdWorker.nextId());
            num = sysDictDao.insert(sysDict);
        }else {
            num = sysDictDao.update(sysDict);
        }
        return num;
    }
}
