package com.touchspring.smartforecasting.service;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.touchspring.smartforecasting.dao.SysLogDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysLog;
import com.touchspring.smartforecasting.repository.sys.SysLogRepository;
import com.touchspring.smartforecasting.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class SysLogService {
    @Autowired
    private SysLogRepository sysLogRepository;
    @Autowired
    private SysLogDao sysLogDao;


    /**
     * 日志记录
     *
     * @param url    .
     * @param userId .
     */
    @Async
    public void log(String url, String userId, String data, String desc) {
        SysLog sysLog = new SysLog();
        sysLog.setId(IdWorker.nextId());
        sysLog.setUrl(url);
        sysLog.setEvent("请求");
        sysLog.setCreateUserId(userId);
        sysLog.setCreateAt(LocalDateTime.now());
        sysLogRepository.save(sysLog);
    }

    /**
     * 记录日志
     *
     * @param url                 请求地址
     * @param userId              用户
     * @param event               事件
     * @param currentObjectValue  当前对象
     * @param previousObjectValue 之前对象
     * @param objectId            对象Id
     * @param keys                需要判断的值
     */
    @Async
    public void log(String url, String userId, String event, Object currentObjectValue, Object previousObjectValue, String objectId, String[] keys) {
        try {
            Map<String, ObjectDifferent> different;
            if (previousObjectValue != null && currentObjectValue != null) {
                different = ObjectUtils.getDifferent(previousObjectValue, currentObjectValue, keys);
            } else if (currentObjectValue != null) {
                different = ObjectUtils.getValues(currentObjectValue, keys);
            } else {
                assert previousObjectValue != null;
                different = ObjectUtils.getRemoveValues(previousObjectValue, keys);
            }
            String json = JSONArray.toJSONString(different);
            SysLog sysLog = new SysLog();
            sysLog.setId(IdWorker.nextId());

            sysLog.setUrl(url);
            sysLog.setEvent(event);
            sysLog.setObjectId(objectId);
            sysLog.setDescription(json);
            sysLog.setCreateUserId(userId);
            sysLog.setCreateAt(LocalDateTime.now());
            sysLogRepository.save(sysLog);
        } catch (
                IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public int save(SysLog sysLog) {
        int num;
        if (sysLog.getId() == null) {
            sysLog.setId(IdWorker.nextId());
            num = sysLogDao.insert(sysLog);
        } else {
            num = sysLogDao.update(sysLog);
        }
        return num;
    }


    /**
     * @param startAt
     * @param endAt
     * @param event
     * @param sort      defaultValue = "createAt"
     * @param direction defaultValue = "DESC"
     * @return
     */
    public PageResult searchByCondition(PageRequestOfMybatis pageRequestOfMybatis, LocalDateTime startAt,
                                   LocalDateTime endAt, String event,
                                         String sort, String direction) {
        int pageNum = pageRequestOfMybatis.getPageNum();
        int pageSize = pageRequestOfMybatis.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<SysLog> sysLogs = sysLogDao.searchByCondition(startAt, endAt, event, direction);
        return PageUtils.getPageResult(pageRequestOfMybatis, new PageInfo<SysLog>(sysLogs));
    }

}
