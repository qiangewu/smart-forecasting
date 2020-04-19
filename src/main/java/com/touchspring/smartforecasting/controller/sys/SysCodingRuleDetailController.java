package com.touchspring.smartforecasting.controller.sys;

import com.touchspring.core.mvc.ui.ResultData;
import com.touchspring.smartforecasting.dao.SysCodingRuleDetailDao;
import com.touchspring.smartforecasting.domain.entity.sys.SysCodingRuleDetail;
import com.touchspring.smartforecasting.service.SysCodingRuleDetailService;
import com.touchspring.smartforecasting.utils.IdWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
public class SysCodingRuleDetailController {


    @Autowired
    private SysCodingRuleDetailDao sysCodingRuleDetailDao;
    @Autowired
    private SysCodingRuleDetailService sysCodingRuleDetailService;
    /**
     * 分页查询信息
     *
     * @param page      .
     * @param size      .
     * @param sort      .
     * @param direction .
     * @return .
     */
    @GetMapping("sys-coding-rule/{codingRuleId}/details")
    public ResultData findAllSysCodingRuleDetails(
            @PathVariable("codingRuleId") String codingRuleId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestParam(value = "sort", defaultValue = "sort") String sort,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        List<SysCodingRuleDetail> sysCodingRuleDetails = sysCodingRuleDetailDao.findBySysCodingRuleIdOrderBySortAsc(codingRuleId);
        return ResultData.ok().putDataValue("sysCodingRuleDetails", sysCodingRuleDetails);
    }

    /**
     * 根据id查询
     *
     * @param id .
     * @return .
     */
    @GetMapping("sys-coding-rule/{codingRuleId}/details/{id}")
    public ResultData findSysCodingRuleDetailById(
            @PathVariable("codingRuleId") String codingRuleId,
            @PathVariable("id") String id) {
        SysCodingRuleDetail sysCodingRuleDetail = sysCodingRuleDetailDao.findById(id);
        if (sysCodingRuleDetail==null) {
            return ResultData.notFound();
        }
        return ResultData.ok().putDataValue("sysCodingRuleDetail", sysCodingRuleDetail);
    }

    /**
     * 增加
     *
     * @param sysCodingRuleDetail .
     * @return .
     */
    @PostMapping("sys-coding-rule/{codingRuleId}/details")
    public ResultData insertSysCodingRuleDetail(
            @PathVariable("codingRuleId") String codingRuleId,
            @RequestBody SysCodingRuleDetail sysCodingRuleDetail) {
        sysCodingRuleDetail.setId(IdWorker.nextId());
        sysCodingRuleDetail.setSort(sysCodingRuleDetail.getId());
        sysCodingRuleDetail.setSysCodingRuleId(codingRuleId);
        switch (sysCodingRuleDetail.getType()) {
            case AUTO:
                String value = String.format("%0" + sysCodingRuleDetail.getLength() + "d", 1);
                sysCodingRuleDetail.setCurrentValue(value);
                sysCodingRuleDetail.setReview(value);
                break;
            case TEXT:
                sysCodingRuleDetail.setReview(sysCodingRuleDetail.getNode());
                break;
            case DATETIME:
                sysCodingRuleDetail.setReview(LocalDateTime.now().format(DateTimeFormatter.ofPattern(sysCodingRuleDetail.getNode())));
                break;
            default:
                break;
        }
        sysCodingRuleDetailDao.insert(sysCodingRuleDetail);
        return ResultData.ok().putDataValue("sysCodingRuleDetails", sysCodingRuleDetail);
    }

    /**
     * 更新信息
     *
     * @param sysCodingRuleDetail .
     * @return .
     */
    @PatchMapping("sys-coding-rule/{codingRuleId}/details/{id}")
    public ResultData updateSysCodingRuleDetail(
            @PathVariable("codingRuleId") String codingRuleId,
            @PathVariable("id") String id,
            @RequestBody SysCodingRuleDetail sysCodingRuleDetail) {
        SysCodingRuleDetail target = sysCodingRuleDetailDao.findById(id);
        if (target==null) {
            return ResultData.notFound();
        }
        BeanUtils.copyProperties(sysCodingRuleDetail, target, "id", "createAt", "updateAt", "createUserId", "updateUserId", "isDeleted","sort");
        target.setSysCodingRuleId(codingRuleId);
        switch (target.getType()) {
            case AUTO:
                target.setReview(target.getCurrentValue());
                break;
            case TEXT:
                target.setReview(sysCodingRuleDetail.getNode());
                break;
            case DATETIME:
                target.setReview(LocalDateTime.now().format(DateTimeFormatter.ofPattern(sysCodingRuleDetail.getNode())));
                break;
            default:
                break;
        }
        sysCodingRuleDetailDao.update(target);
        return ResultData.ok();
    }

    /**
     * 根据Id删除
     *
     * @param id .
     * @return .
     */
    @DeleteMapping("sys-coding-rule/{codingRuleId}/details/{id}")
    public ResultData deleteSysCodingRuleDetail(
            @PathVariable("codingRuleId") String codingRuleId,
            @PathVariable("id") String id) {
        sysCodingRuleDetailDao.deleteById(id);
        return ResultData.ok();
    }

    /**
     * 菜单上移
     *
     * @param id .
     * @return .
     */
    @PatchMapping("sys-coding-rule/{codingRuleId}/details/up/{id}")
    public ResultData upSysDictData(@PathVariable("codingRuleId") String codingRuleId,@PathVariable("id") String id) {
        SysCodingRuleDetail target = sysCodingRuleDetailDao.findById(id);
        if (target==null) {
            return ResultData.notFound();
        }
        List<SysCodingRuleDetail> sysCodingRuleDetail = sysCodingRuleDetailDao.findBySysCodingRuleIdOrderBySortAsc(target.getSysCodingRuleId());

        sysCodingRuleDetail.sort((a, b) -> -a.getSort().compareTo(b.getSort()));
        Optional<SysCodingRuleDetail> lessOptional = sysCodingRuleDetail.stream().filter(x -> x.getSort().compareTo(target.getSort()) < 0).findFirst();

        if (!lessOptional.isPresent()) {
            ResultData resultData = ResultData.errorRequest();
            resultData.setMessage("菜单无法上移");
            return resultData;
        }
        SysCodingRuleDetail less = lessOptional.get();
        swapSysCodingRuleDetailSort(target, less);
        return ResultData.ok();
    }

    /**
     * 菜单下移
     *
     * @param id .
     * @return .
     */
    @PatchMapping("sys-coding-rule/{codingRuleId}/details/down/{id}")
    public ResultData downSysCodingRuleDetail(@PathVariable("codingRuleId") String codingRuleId,@PathVariable("id") String id) {
        SysCodingRuleDetail target = sysCodingRuleDetailDao.findById(id);
        if (target==null) {
            return ResultData.notFound();
        }
        List<SysCodingRuleDetail> sysCodingRuleDetail = sysCodingRuleDetailDao.findBySysCodingRuleIdOrderBySortAsc(target.getSysCodingRuleId());
        sysCodingRuleDetail.sort(Comparator.comparing(SysCodingRuleDetail::getSort));
        Optional<SysCodingRuleDetail> greaterOptional = sysCodingRuleDetail.stream().filter(x -> x.getSort().compareTo(target.getSort()) > 0).findFirst();
        if (!greaterOptional.isPresent()) {
            ResultData resultData = ResultData.errorRequest();
            resultData.setMessage("菜单无法下移");
            return resultData;
        }
        SysCodingRuleDetail greater = greaterOptional.get();
        swapSysCodingRuleDetailSort(target, greater);
        return ResultData.ok();
    }

    private void swapSysCodingRuleDetailSort(SysCodingRuleDetail a, SysCodingRuleDetail b) {
        String tempSort = b.getSort();
        b.setSort(a.getSort());
        a.setSort(tempSort);
        sysCodingRuleDetailDao.update(b);
        sysCodingRuleDetailDao.update(a);
    }
}
