package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.core.utils.StringUtils;
import com.touchspring.smartforecasting.dao.SysDictDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysDict;
import com.touchspring.smartforecasting.service.SysDictService;
import com.touchspring.smartforecasting.utils.IdWorker;
import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SysDictController {


    @Autowired
    private SysDictDao sysDictDao;
    @Autowired
    private SysDictService sysDictService;

    /**
     * 查询全部信息
     *
     * @return .
     */

    @GetMapping(value = "dictionaries", params = "scope=all")
    public ResultData findAllSysDictionaries(String code) {
        List<SysDict> sysDicts = sysDictDao.findByCode(code);
        return ResultData.ok().putDataValue("sysDictionaries", sysDicts);
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
    @GetMapping(value = "dictionaries", params = {"page", "size"})
    public ResultData findAllSysDictionaries(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "createAt") String sort,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction) {
        PageResult sysPage;
        if (StringUtils.equals("ASC", direction.toUpperCase())) {
            direction = "ASC";
        } else {
            direction = "DESC";
        }
        sysPage = sysDictService.findPage(new PageRequestOfMybatis(page, size),direction);
        long totalAmount = sysPage.getTotalSize();
        List<SysDict> sysDictionaries = (List<SysDict>)sysPage.getContent();
        return ResultData.ok().putDataValue("sysDictionaries", sysDictionaries).putDataValue("totalAmount", totalAmount);
    }

    /**
     * 根据id查询
     *
     * @param id .
     * @return .
     */
    @GetMapping("dictionary/{id}")
    public ResultData findSysDictById(@PathVariable("id") String id) {
        SysDict sysDict = sysDictDao.findById(id);
        if (sysDict==null) {
            return ResultData.notFound();
        }
        return ResultData.ok().putDataValue("sysDictionary", sysDict);
    }

    /**
     * 增加
     *
     * @param sysDict .
     * @return .
     */
    @PostMapping("dictionary")
    public ResultData insertSysDict(@RequestBody SysDict sysDict) {
        sysDictService.save(sysDict);
        return ResultData.ok().putDataValue("sysDictionary", sysDict);
    }

    /**
     * 更新信息
     *
     * @param sysDict .
     * @return .
     */
    @PatchMapping("dictionary/{id}")
    public ResultData updateSysDict(@PathVariable("id") String id, @RequestBody SysDict sysDict) {
        SysDict target = sysDictDao.findById(id);
        if (target==null) {
            return ResultData.notFound();
        }
        BeanUtils.copyProperties(sysDict, target, "id", "createAt", "updateAt", "createUserId", "updateUserId", "isDeleted");
        sysDictService.save(target);
        return ResultData.ok();
    }

    /**
     * 根据Id删除
     *
     * @param id .
     * @return .
     */
    @DeleteMapping("dictionary/{id}")
    public ResultData deleteSysDict(@PathVariable("id") String id) {
        sysDictDao.deleteById(id);
        return ResultData.ok();
    }


}
