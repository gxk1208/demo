package com.auto.demo.controller;


import com.auto.demo.common.JsonResult;
import com.auto.demo.common.PageParam;
import com.auto.demo.common.PagedList;
import com.auto.demo.dto.LayoutConfigDto;
import com.auto.demo.entity.ApprovalConfig;
import com.auto.demo.entity.SelfField;
import com.auto.demo.service.ApprovalConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangfuliang
 * @version 1.0
 * @date 2020/6/8 14:18
 */
@RestController
@RequestMapping("/api/approval/config")
@Api(tags = "工作流配置")
@Slf4j
public class ApprovalConfigController {
    @Autowired
    ApprovalConfigService approvalService;

    @ApiOperation(value = "增加工作流")
    @PostMapping("/add")
    public JsonResult<Integer> add(@RequestBody ApprovalConfig approvalConfig) {
        return JsonResult.success(approvalService.addWorkFlowConfig(approvalConfig));
    }

    @ApiOperation(value = "编辑工作流")
    @PostMapping("/modify")
    public JsonResult<Integer> modify(@RequestBody ApprovalConfig approvalConfig) {
        return JsonResult.success(approvalService.modifyWorkFlowConfig(approvalConfig));
    }

    @ApiOperation(value = "删除工作流")
    @PostMapping("/del")
    public JsonResult<Integer> del(@RequestParam(value = "id") Integer id) {
        approvalService.delWorkFlowConfig(id);
        return JsonResult.success();
    }

    @ApiOperation("获取流程配置列表")
    @GetMapping("/page")
    public JsonResult<PagedList<ApprovalConfig>> page(PageParam pageParam) {
        return JsonResult.success(approvalService.pageList(pageParam));
    }

    @ApiOperation("获取流程配置列表")
    @GetMapping("/get")
    public JsonResult<ApprovalConfig> page(@RequestParam("id") Integer id) {
        return JsonResult.success(approvalService.getApprovalConfig(id));
    }

    @ApiOperation("获取实体的可见布局")
    @GetMapping("/get-field-layout")
    public JsonResult<LayoutConfigDto> getFieldLayouts(int entityId) {
        return JsonResult.success(approvalService.getFieldLayout(entityId));
    }

    @ApiOperation(value = "增加流程实体字段")
    @PostMapping("/add-flow-filed")
    public JsonResult<Void> addFlowFiled(@RequestParam(value = "entityId") Integer entityId, @RequestBody List<SelfField> fieldsList) {
        approvalService.addWorkflowFields(entityId, fieldsList);
        return JsonResult.success();
    }


}
