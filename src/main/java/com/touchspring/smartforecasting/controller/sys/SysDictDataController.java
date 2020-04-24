package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.core.utils.StringUtils;
import com.touchspring.smartforecasting.domain.entity.sys.SysDictData;
import com.touchspring.smartforecasting.repository.sys.SysDictDataRepository;
import com.touchspring.smartforecasting.service.SysDictDateService;
import com.touchspring.smartforecasting.utils.IdWorker;
import com.touchspring.smartforecasting.utils.PageRequestOfMybatis;
import com.touchspring.smartforecasting.utils.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
public class SysDictDataController {


    private final SysDictDataRepository sysDictDataRepository;

    private final SysDictDateService sysDictDateService;

    public SysDictDataController(SysDictDataRepository sysDictDataRepository,SysDictDateService sysDictDateService) {
        this.sysDictDataRepository = sysDictDataRepository;
        this.sysDictDateService = sysDictDateService;
    }

    /**
     * 查询全部信息
     *
     * @return .
     */

    @GetMapping("sys-dict/{sysDictId}/data/all")
    public ResultData findAllSysDictData(@PathVariable("sysDictId") String sysDictId) {
        List<SysDictData> sysDictData = sysDictDataRepository.findBySysDictIdOrderBySortAsc(sysDictId);
        return ResultData.ok().putDataValue("sysDictData", sysDictData);
    }

    /**
     * 分页查询信息
     *
     * @param sysDictId .
     * @param page      .
     * @param size      .
     * @param sort      .
     * @param direction .
     * @return .
     */
    @GetMapping("sys-dict/{sysDictId}/data")
    public ResultData findAllSysDictData(
            @PathVariable("sysDictId") String sysDictId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "sort") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        PageResult sysDictDatePage;
        if (StringUtils.equals("ASC", direction.toUpperCase())) {
            direction = "ASC";
        } else {
            direction = "DESC";
        }
        sysDictDatePage = sysDictDateService.findPage(new PageRequestOfMybatis(page, size),direction);
        long totalAmount = sysDictDatePage.getTotalSize();
        List<SysDictData> sysDictDates = (List<SysDictData>) sysDictDatePage.getContent();
        return ResultData.ok().putDataValue("sysDictData", sysDictDates).putDataValue("totalAmount", totalAmount);
    }

    /**
     * 根据id查询
     *
     * @param id .
     * @return .
     */
    @GetMapping("sys-dict/{sysDictId}/data/{id}")
    public ResultData findSysDictDataById(
            @PathVariable("sysDictId") String sysDictId,
            @PathVariable("id") String id) {
        Optional<SysDictData> sysDictDataOptional = sysDictDataRepository.findById(id);
        if (!sysDictDataOptional.isPresent()) {
            return ResultData.notFound();
        }
        SysDictData sysDictData = sysDictDataOptional.get();
        return ResultData.ok().putDataValue("sysDictData", sysDictData);
    }

    /**
     * 增加
     *
     * @param sysDictData .
     * @return .
     */
    @PostMapping("sys-dict/{sysDictId}/data")
    public ResultData insertSysDictData(
            @PathVariable("sysDictId") String sysDictId,
            @RequestBody SysDictData sysDictData) {
        sysDictData.setId(IdWorker.nextId());
        sysDictData.setSort(sysDictData.getId());
        sysDictDataRepository.save(sysDictData);
        return ResultData.ok().putDataValue("sysDictData", sysDictData);
    }

    /**
     * 更新信息
     *
     * @param sysDictData .
     * @return .
     */
    @PatchMapping("sys-dict/{sysDictId}/data/{id}")
    public ResultData updateSysDictData(
            @PathVariable("sysDictId") String sysDictId,
            @PathVariable("id") String id, @RequestBody SysDictData sysDictData) {
        Optional<SysDictData> sysDictDataOptional = sysDictDataRepository.findById(id);
        if (!sysDictDataOptional.isPresent()) {
            return ResultData.notFound();
        }
        SysDictData target = sysDictDataOptional.get();
        BeanUtils.copyProperties(sysDictData, target, "id", "createAt", "updateAt", "createUserId", "updateUserId", "isDeleted","sort");
        sysDictDataRepository.save(target);
        return ResultData.ok();
    }

    /**
     * 根据Id删除
     *
     * @param id .
     * @return .
     */
    @DeleteMapping("sys-dict/{sysDictId}/data/{id}")
    public ResultData deleteSysDictData(
            @PathVariable("sysDictId") String sysDictId,
            @PathVariable("id") String id) {
        sysDictDataRepository.deleteById(id);
        return ResultData.ok();
    }

    /**
     * 菜单上移
     *
     * @param id .
     * @return .
     */
    @PatchMapping("sys-dict/{sysDictId}/data/up/{id}")
    public ResultData upSysDictData(@PathVariable("sysDictId") String sysDictId,@PathVariable("id") String id) {
        Optional<SysDictData> sysDictDataOptional = sysDictDataRepository.findById(id);
        if (!sysDictDataOptional.isPresent()) {
            return ResultData.notFound();
        }
        SysDictData target = sysDictDataOptional.get();
        List<SysDictData> sysDictData = sysDictDataRepository.findBySysDictIdOrderBySortAsc(target.getSysDictId());

        sysDictData.sort((a, b) -> -a.getSort().compareTo(b.getSort()));
        Optional<SysDictData> lessOptional = sysDictData.stream().filter(x -> x.getSort().compareTo(target.getSort()) < 0).findFirst();

        if (!lessOptional.isPresent()) {
            ResultData resultData = ResultData.errorRequest();
            resultData.setMessage("菜单无法上移");
            return resultData;
        }
        SysDictData less = lessOptional.get();
        swapSysDictDataSort(target, less);
        return ResultData.ok();
    }

    /**
     * 菜单下移
     *
     * @param id .
     * @return .
     */
    @PatchMapping("sys-dict/{sysDictId}/data/down/{id}")
    public ResultData downSysDictData(@PathVariable("sysDictId") String sysDictId,@PathVariable("id") String id) {
        Optional<SysDictData> sysDictDataOptional = sysDictDataRepository.findById(id);
        if (!sysDictDataOptional.isPresent()) {
            return ResultData.notFound();
        }
        SysDictData target = sysDictDataOptional.get();
        List<SysDictData> sysDictData = sysDictDataRepository.findBySysDictIdOrderBySortAsc(target.getSysDictId());
        sysDictData.sort(Comparator.comparing(SysDictData::getSort));
        Optional<SysDictData> greaterOptional = sysDictData.stream().filter(x -> x.getSort().compareTo(target.getSort()) > 0).findFirst();
        if (!greaterOptional.isPresent()) {
            ResultData resultData = ResultData.errorRequest();
            resultData.setMessage("菜单无法下移");
            return resultData;
        }
        SysDictData greater = greaterOptional.get();
        swapSysDictDataSort(target, greater);
        return ResultData.ok();
    }

    private void swapSysDictDataSort(SysDictData a, SysDictData b) {
        String tempSort = b.getSort();
        b.setSort(a.getSort());
        a.setSort(tempSort);
        sysDictDataRepository.save(b);
        sysDictDataRepository.save(a);
    }
}
