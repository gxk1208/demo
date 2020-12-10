package com.auto.demo.controller;

import com.auto.demo.common.JsonResult;
import com.auto.demo.dto.LayoutConfigDto;
import com.auto.demo.dto.SelfFieldDto;
import com.auto.demo.entity.PickList;
import com.auto.demo.entity.SelfField;
import com.auto.demo.service.FieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/5/19 14:26
 */
@RestController
@RequestMapping("/field")
@Api("实体字段操作")
@Slf4j
public class FieldController {
    @Autowired
    private FieldService fieldService;

    @PostMapping("/add")
    @ApiOperation(value = "新增实体字段")
    public JsonResult<Integer> addField(@RequestBody SelfField field){
        return JsonResult.success(fieldService.addField(field));
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取指定字段信息")
    public JsonResult<SelfFieldDto> getField(@RequestParam("id") Integer id){
        return JsonResult.success(fieldService.getField(id));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改实体字段")
    public JsonResult<Integer> updateField(@RequestBody SelfField field){
        return JsonResult.success(fieldService.updateField(field));
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除实体字段")
    public JsonResult<Integer> deleteField(@RequestParam("id") Integer id){
        return JsonResult.success(fieldService.deleteField(id));
    }

    @GetMapping("/get-all")
    @ApiOperation(value = "查看指定实体的所有字段")
    public JsonResult<List<SelfField>> getAllField(@RequestParam("id") Integer id , @RequestParam("query") String query){
        return JsonResult.success(fieldService.getAllField(id,query));
    }

    @GetMapping("/get-name-fields")
    @ApiOperation(value = "获取指定实体可以作为名称字段的字段集合")
    public JsonResult<List<SelfField>> getNameFields(@RequestParam("id") Integer id){
        return JsonResult.success(fieldService.getNameFields(id));
    }

    @PostMapping("/add-pickList")
    @ApiOperation(value = "添加下拉列表类型字段的选项")
    public JsonResult<Integer> addPickList(@RequestParam("entityId")Integer entityId,@RequestParam("fieldId")Integer fieldId,@RequestBody List<PickList> pickLists){
        return JsonResult.success(fieldService.addPickList(entityId,fieldId,pickLists));
    }

    @GetMapping("/get-pickList")
    @ApiOperation(value = "获取下拉列表类型字段的选项")
    public JsonResult<List<PickList>> getPickList(@RequestParam("entityId") Integer entityId,@RequestParam("fieldId") Integer fieldId){
        return JsonResult.success(fieldService.getPickList(entityId,fieldId));
    }

    @GetMapping("/get-default-layout")
    @ApiOperation(value = "获取指定实体默认字段布局")
    public JsonResult<List<SelfField>> getDefaultlayout(@RequestParam("id") Integer id){
        return JsonResult.success(fieldService.getDefaultLayout(id));
    }

    @GetMapping("/get-show-fields")
    @ApiOperation(value = "获取指定实体可以展示的字段")
    public JsonResult<List<SelfField>> getAllShowField(@RequestParam("id") Integer id){
        return JsonResult.success(fieldService.getAllShowField(id));
    }

    @PostMapping("/update-field-layout")
    @ApiOperation(value = "修改字段显示列表布局")
    public JsonResult<Integer> updateFieldLaout(@RequestBody LayoutConfigDto layoutConfigDto){
        return JsonResult.success(fieldService.updateFieldLayout(layoutConfigDto));
    }

    @PostMapping ("/save-field-layout")
    @ApiOperation(value = "保存字段显示列表布局")
    public JsonResult<Integer> saveFieldLayout(@RequestBody LayoutConfigDto layoutConfigDto){
        return JsonResult.success(fieldService.saveFieldLayout(layoutConfigDto));
    }

    @GetMapping ("/get-field-layouts")
    @ApiOperation(value = "获取字段布局列表")
    public JsonResult<List<LayoutConfigDto>> getFieldLayouts(@RequestParam("entityId") Integer entityId){
        return JsonResult.success(fieldService.getFieldLayouts(entityId));
    }

    @GetMapping ("/get-field-layout")
    @ApiOperation(value = "获取指定字段布局")
    public JsonResult<LayoutConfigDto> getFieldLayout(@RequestParam("layoutId") Integer layoutId){
        return JsonResult.success(fieldService.getFieldLayout(layoutId));
    }

    @GetMapping ("/get-custom-field")
    @ApiOperation(value = "获取实体自定义字段")
    public JsonResult<List<SelfField>> getCustomField(@RequestParam("entityId") Integer entityId,@RequestParam("accountId") Integer accountId){
        return JsonResult.success(fieldService.getCustomField(entityId,accountId));
    }

    @GetMapping ("/get-target-field")
    @ApiOperation(value = "获取实体目标字段")
    public JsonResult<List<SelfField>> getTargetField(@RequestParam("fieldId") Integer fieldId,@RequestParam("entityId") Integer entityId){
        return JsonResult.success(fieldService.getTargetField(entityId));
    }

}
