package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.core.utils.StringUtils;
import com.touchspring.smartforecasting.dao.SysI18NDetailDao;
import com.touchspring.smartforecasting.dao.SysLanguageDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysI18NDetail;
import com.touchspring.smartforecasting.domain.entity.sys.SysLanguage;
import com.touchspring.smartforecasting.service.SysLanguageService;
import com.touchspring.smartforecasting.utils.IdWorker;
import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LanguageController {

    @Autowired
    private SysLanguageService sysLanguageService;
    @Autowired
    private SysLanguageDao sysLanguageDao;
    @Autowired
    private SysI18NDetailDao sysI18NDetailDao;



    /**
     * 分页查询信息
     *
     * @return .
     */
    @GetMapping(value = "languages", params = "scope=all")
    public ResultData findAllLanguages() {
        List<SysLanguage> languages = sysLanguageDao.findAll();
        List<SysI18NDetail> i18NDetails = sysI18NDetailDao.findAll();
        return ResultData.ok()
                .putDataValue("languages", languages)
                .putDataValue("i18NDetails", i18NDetails);
    }

    /**
     * 分页查询信息
     *
     * @param page      .
     * @param size      .
     * @param sort      .
     * @param direction .
     * @return .
     */
    @GetMapping("languages")
    public ResultData findAllLanguages(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "createAt") String sort,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        PageResult sysLanguagesPage;
        if (StringUtils.equals("ASC", direction.toUpperCase())) {
            direction = "ASC";
        } else {
            direction = "DESC";
        }
        sysLanguagesPage = sysLanguageService.findPage(new PageRequestOfMybatis(page, size), direction);
        long totalAmount = sysLanguagesPage.getTotalSize();
        List<SysLanguage> sysLanguages = (List<SysLanguage>)sysLanguagesPage.getContent();
        return ResultData.ok().putDataValue("languages", sysLanguages).putDataValue("totalAmount", totalAmount);
    }

    /**
     * 根据id查询
     *
     * @param id .
     * @return .
     */
    @GetMapping("languages/{id}")
    public ResultData findLanguageById(@PathVariable("id") String id) {
        SysLanguage sysLanguage = sysLanguageDao.findById(id);
        if (sysLanguage==null) {
            return ResultData.notFound();
        }
        return ResultData.ok().putDataValue("language", sysLanguage);
    }

    /**
     * 增加
     *
     * @param sysLanguage .
     * @return .
     */
    @PostMapping("languages")
    public ResultData insertLanguage(@RequestBody SysLanguage sysLanguage) {
        sysLanguage.setId(IdWorker.nextId());
        sysLanguageDao.insert(sysLanguage);
        return ResultData.ok().putDataValue("languages", sysLanguage);
    }

    /**
     * 更新信息
     *
     * @param sysLanguage .
     * @return .
     */
    @PatchMapping("languages/{id}")
    public ResultData updateLanguage(@PathVariable("id") String id, @RequestBody SysLanguage sysLanguage) {
        SysLanguage targetSysLanguage = sysLanguageDao.findById(id);
        if (targetSysLanguage==null) {
            return ResultData.notFound();
        }
        BeanUtils.copyProperties(sysLanguage, targetSysLanguage, "id", "name", "icon", "code", "createAt", "updateAt", "createUserId", "updateUserId", "isDeleted");
        sysLanguageDao.update(targetSysLanguage);
        return ResultData.ok();
    }

    /**
     * 根据Id删除
     *
     * @param id .
     * @return .
     */
    @DeleteMapping("languages/{id}")
    public ResultData deleteLanguage(@PathVariable("id") String id) {
        sysLanguageDao.deleteById(id);
        return ResultData.ok();
    }
}
