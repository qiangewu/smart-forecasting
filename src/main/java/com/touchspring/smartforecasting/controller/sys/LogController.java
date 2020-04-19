package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.core.utils.StringUtils;
import com.touchspring.smartforecasting.dao.SysLogDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysLog;
import com.touchspring.smartforecasting.service.SysLogService;
import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class LogController {
    @Autowired
    private SysLogDao sysLogDao;
    @Autowired
    private SysLogService sysLogService;

    /**
     * 分页查询信息
     *
     * @param page      .
     * @param size      .
     * @param sort      .
     * @param direction .
     * @return .
     */
    @GetMapping("logs")
    public ResultData findAllSysLogs(
            @RequestParam(value = "startAt", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startAt,
            @RequestParam(value = "endAt", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endAt,
            @RequestParam(value = "event", required = false) String event,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "createAt") String sort,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        PageResult sysLogPage;
        if (StringUtils.equals("ASC", direction.toUpperCase())) {
            direction = "ASC";
        } else {
            direction = "DESC";
        }
        sysLogPage = sysLogService.searchByCondition(new PageRequestOfMybatis(page, size), startAt, endAt, event, sort, direction);
        long totalAmount = sysLogPage.getTotalSize();
        List<SysLog> sysLogs = (List<SysLog>)sysLogPage.getContent();
        return ResultData.ok().putDataValue("logs", sysLogs).putDataValue("totalAmount", totalAmount);
    }

}
