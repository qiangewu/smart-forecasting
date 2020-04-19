package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.smartforecasting.dao.SysI18NDetailDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysI18NDetail;
import com.touchspring.smartforecasting.service.SysI18NDetailService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Data
class I18nDetailRequest {
    private String languageCode;
    private String i18nCode;
    private String value;
}

@RestController
public class I18NDetailController {

    @Autowired
    private SysI18NDetailService sysI18NDetailService;
    @Autowired
    private SysI18NDetailDao sysI18NDetailDao;


    /**
     * 查询全部
     *
     * @return .
     */
    @GetMapping("i18n-details")
    public ResultData findI18NDetails() {
        return ResultData.ok().putDataValue("i18NDetails", sysI18NDetailDao.findAll());
    }


    /**
     * 根据 i8nCode 查询
     *
     * @param i8nCode .
     * @return .
     */
    @GetMapping("i18n-details/{i8nCode}")
    public ResultData findI18NDetailById(@PathVariable("i8nCode") String i8nCode) {
        List<SysI18NDetail> i18NDetails = sysI18NDetailDao.findByI18nCode(i8nCode);
        return ResultData.ok().putDataValue("i18NDetails", i18NDetails);
    }


    /**
     * @param i8nCode        .
     * @param detailRequests .
     * @return .
     */
    @PatchMapping("i18n-details/{i8nCode}")
    public ResultData updateI18NDetail(@PathVariable("i8nCode") String i8nCode, @RequestBody List<I18nDetailRequest> detailRequests) {
        sysI18NDetailDao.deleteByI18nCode(i8nCode);
        for (I18nDetailRequest detailRequest : detailRequests) {
            SysI18NDetail sysI18NDetail = new SysI18NDetail();
            sysI18NDetail.setI18nCode(i8nCode);
            sysI18NDetail.setLanguageCode(detailRequest.getLanguageCode());
            sysI18NDetail.setValue(detailRequest.getValue());
            sysI18NDetailService.save(sysI18NDetail);
        }
        return ResultData.ok();
    }

    /**
     * 根据Id删除
     *
     * @param i8nCode .
     * @return .
     */
    @DeleteMapping("i18n-details/{i8nCode}")
    public ResultData deleteI18NDetail(@PathVariable("i8nCode") String i8nCode) {
        sysI18NDetailDao.deleteByI18nCode(i8nCode);
        return ResultData.ok();
    }
}
