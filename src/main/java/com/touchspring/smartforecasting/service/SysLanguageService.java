package com.touchspring.smartforecasting.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touchspring.smartforecasting.dao.SysLanguageDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysCodingRule;
import com.touchspring.smartforecasting.domain.entity.sys.SysLanguage;
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
public class SysLanguageService extends EntityInitService<SysLanguage> implements PageHelperService, BaseService<SysLanguage> {
    @Autowired
    private SysLanguageDao sysLanguageDao;


    @Override
    public PageResult findPage(PageRequestOfMybatis pageRequestOfMybatis, String direction) {
        return PageUtils.getPageResult(pageRequestOfMybatis, getPageInfo(pageRequestOfMybatis,direction));
    }

    /**
     * 调用分页插件完成分页
     * @param pageRequestOfMybatis
     * @return
     */
    private PageInfo<SysLanguage> getPageInfo(PageRequestOfMybatis pageRequestOfMybatis,String direction) {
        int pageNum = pageRequestOfMybatis.getPageNum();
        int pageSize = pageRequestOfMybatis.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SysLanguage> list = sysLanguageDao.selectPage(direction);
        return new PageInfo<SysLanguage>(list);
    }


    @Override
    public int save(SysLanguage sysLanguage) {
        int num;
        if(sysLanguage.getId() == null){
            preInsert(sysLanguage);
            num = sysLanguageDao.insert(sysLanguage);
        }else {
            preUpdate(sysLanguage);
            num = sysLanguageDao.update(sysLanguage);
        }
        return num;
    }
}
