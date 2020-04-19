package com.touchspring.smartforecasting.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touchspring.smartforecasting.dao.SysUserDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysUser;
import com.touchspring.smartforecasting.repository.sys.SysUserRepository;
import com.touchspring.smartforecasting.service.base.BaseService;
import com.touchspring.smartforecasting.service.base.PageHelperService;
import com.touchspring.smartforecasting.utils.IdWorker;
import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;
import com.touchspring.smartforecasting.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysUserService implements BaseService<SysUser>, PageHelperService {

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysUserDao sysUserDao;


//    public void asyncUsersFromMES() {
//        RestTemplate restTemplate = new RestTemplate();
//        MESUserResult mesUserResult = restTemplate.getForObject(mesUserApi
//                , MESUserResult.class);
//        assert mesUserResult != null;
//        for (MESUser mesUser : mesUserResult.getData()) {
//            Optional<SysUser> userOptional = sysUserRepository.findById(mesUser.getId());
//            Optional<SysUser> userOptionalByLoginName = sysUserRepository.findByLoginName(mesUser.getName());
//            if ((!userOptional.isPresent()) && (!userOptionalByLoginName.isPresent())) {
//                SysUser sysUser = new SysUser();
//                sysUser.setId(mesUser.getId());
//                sysUser.setId(mesUser.getId());
//                sysUser.setIsActive(SimpleEnums.YES);
//                sysUser.setLoginName(mesUser.getId());
//                sysUser.setUserName(mesUser.getName());
//                sysUser.setIsDeleted(SimpleStatus.NO.getCode());
//                sysUserRepository.save(sysUser);
//            }
//        }
//
//    }


    @Override
    public int save(SysUser sysUser) {
        int num;
        if(sysUser.getId() == null){
            sysUser.setId(IdWorker.nextId());
            num = sysUserDao.insert(sysUser);
        }else {
            num = sysUserDao.update(sysUser);
        }
        return num;
    }

    @Override
    public PageResult findPage(PageRequestOfMybatis pageRequestOfMybatis, String direction) {
        return PageUtils.getPageResult(pageRequestOfMybatis, getPageInfo(pageRequestOfMybatis,direction));
    }

    /**
     * 调用分页插件完成分页
     * @param pageRequestOfMybatis
     * @return
     */
    private PageInfo<SysUser> getPageInfo(PageRequestOfMybatis pageRequestOfMybatis, String direction) {
        int pageNum = pageRequestOfMybatis.getPageNum();
        int pageSize = pageRequestOfMybatis.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> sysUsers = sysUserDao.selectPage(direction);
        return new PageInfo<SysUser>(sysUsers);
    }

}
