package com.auto.demo.controller;

import com.auto.demo.common.JsonResult;
import com.auto.demo.common.PageParam;
import com.auto.demo.dto.AttachmentDto;
import com.auto.demo.dto.BackfillConfigDto;
import com.auto.demo.dto.LayoutConfigDto;
import com.auto.demo.entity.BackfillConfig;
import com.auto.demo.entity.FormLayout;
import com.auto.demo.entity.SelfField;
import com.auto.demo.param.BackfillParam;
import com.auto.demo.param.QueryAttachParam;
import com.auto.demo.service.DataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/5/26 13:36
 */
@RestController
@RequestMapping("/data")
@Api("生成数据操作")
@Slf4j
public class DataController {
    @Autowired
    private DataService dataService;

    @PostMapping("/add-data")
    @ApiOperation(value = "新增实体数据")
    public JsonResult<Integer> addData( @RequestParam("id") Integer id,@RequestBody Map<String, Object> map){
        return JsonResult.success(dataService.addData(id,map));
    }

    @GetMapping("/get-all-data")
    @ApiOperation(value = "获取实体数据")
    public JsonResult<Map<String, Object>> getAllData(PageParam pageParam, @RequestParam("id") Integer id, @RequestParam("panme") Integer pname, @RequestParam("flag") String flag){
        return JsonResult.success(dataService.getAllData(pageParam,id,pname,flag));
    }

    @GetMapping("/get-data")
    @ApiOperation(value = "获取一条实体数据")
    public JsonResult<List<Map<String, Object>>> getOneData(@RequestParam("id") Integer id,@RequestParam("dataId") Integer dataId){
        return JsonResult.success(dataService.getOneData(id,dataId));
    }

    @PostMapping("/update-data")
    @ApiOperation(value = "修改实体数据")
    public JsonResult<Integer> updateData(@RequestBody Map<String, Object> map,@RequestParam("id") Integer id){
        return JsonResult.success(dataService.updateData(id,map));
    }

    @GetMapping("/delete-data")
    @ApiOperation(value = "删除实体数据")
    public JsonResult<Integer> deleteData(@RequestParam("id") Integer id,@RequestParam("dataId") Integer dataId){
        return JsonResult.success(dataService.deleteData(id,dataId));
    }

    @GetMapping("/get-field-value")
    @ApiOperation(value = "获取引用实体名称字段数据集合")
    public JsonResult<List<Map<String,Object>>> getNameFieldValue(@RequestParam("id") Integer id,@RequestParam("query") String query){
        return JsonResult.success(dataService.getNameFieldValue(id,query));
    }

    @PostMapping("/add-form-layout")
    @ApiOperation(value = "保存表单布局配置")
    public JsonResult<Integer> addLayout(@RequestBody List<FormLayout> layouts){
        return JsonResult.success(dataService.addLayout(layouts));
    }

    @GetMapping("/get-form-layout")
    @ApiOperation(value = "获取表单布局")
    public JsonResult<List<SelfField>> getLayout(@RequestParam("entityId") Integer entityId){
        return JsonResult.success(dataService.getLayout(entityId));
    }

    @GetMapping("/get-form")
    @ApiOperation(value = "获取用户新建编辑打开数据时的表单布局")
    public JsonResult<Map<String, Object>> getForm(@RequestParam("entityId") Integer entityId, @RequestParam("dataId") Integer dataId,@RequestParam("flag")boolean flag){
        return JsonResult.success(dataService.getForm(entityId,dataId,flag));
    }

    @PostMapping("/add-menu-layout")
    @ApiOperation(value = "添加菜单布局")
    public JsonResult<Integer> addMenuLayout(@RequestBody LayoutConfigDto layoutConfigDto){
        return JsonResult.success(dataService.addMenuLayout(layoutConfigDto));
    }

    @GetMapping ("/get-menu-layouts")
    @ApiOperation(value = "获取所有菜单布局")
    public JsonResult<List<LayoutConfigDto>> getMenuLayouts(){
        return JsonResult.success(dataService.getMenuLayouts());
    }

    @GetMapping ("/get-menu-layout")
    @ApiOperation(value = "获取指定菜单布局")
    public JsonResult<LayoutConfigDto> getMenuLayout(@RequestParam("id")Integer id){
        return JsonResult.success(dataService.getMenuLayout(id));
    }

    @GetMapping ("/get-default-menu")
    @ApiOperation(value = "获取默认菜单布局")
    public JsonResult<LayoutConfigDto> getDefaultMenuLayout(){
        return JsonResult.success(dataService.getDefaultMenuLayout());
    }

    @GetMapping ("/get-attach")
    @ApiOperation(value = "获取附件列表")
    public JsonResult<List<AttachmentDto>> getAttach(QueryAttachParam param){
        return JsonResult.success(dataService.getAttach(param));
    }

    @PostMapping ("/dispatch-data")
    @ApiOperation(value = "分派数据")
    public JsonResult<Integer> dispatchData(@RequestParam("entityId") Integer entityId,@RequestParam("dataId") Integer dataId,@RequestParam("userId") Integer userId){
        return JsonResult.success(dataService.dispatchData(entityId,dataId,userId));
    }

    @GetMapping("/export-excel")
    @ApiOperation("导出Excel")
    public void exportExcel(@RequestParam("entityId") Integer entityId, @RequestParam("idList")String idList, @ApiIgnore HttpServletResponse response) throws IOException {
        dataService.exportExcel(entityId,idList,response);

    }

    @GetMapping("/get-header")
    @ApiOperation("获取表头")
    public JsonResult<List<String>> getHeader(@RequestParam("path") String path) throws IOException {
        return JsonResult.success(dataService.getHeader(path));
    }

    @ApiOperation("导入Excel")
    @GetMapping("/import-excel")
    public JsonResult<Integer> importExcel(@RequestParam("path") String path, @RequestParam("entityId") Integer entityId, @RequestParam("fieldIds") String fieldIds) throws Exception {
        return JsonResult.success(dataService.importExcel(path,entityId,fieldIds));
    }

    @ApiOperation("添加表单回填配置")
    @PostMapping("/add-backfill")
    public JsonResult<Integer> saveBackfill(@RequestBody BackfillConfig backfillConfig) {
        return JsonResult.success(dataService.saveBackfill(backfillConfig));
    }

    @ApiOperation("修改表单回填配置")
    @PostMapping("/update-backfill")
    public JsonResult<Integer> updateBackfill(@RequestBody BackfillConfig backfillConfig) {
        return JsonResult.success(dataService.saveBackfill(backfillConfig));
    }

    @ApiOperation("获取引用字段所有表单回填配置")
    @GetMapping("/get-backfills")
    public JsonResult<List<BackfillConfigDto>> getBackfills(@RequestParam("entityId")Integer entityId, @RequestParam("fieldId")Integer fieldId) {
        return JsonResult.success(dataService.getBackfills(entityId,fieldId));
    }

    @ApiOperation("获取表单回填配置")
    @GetMapping("/get-backfill")
    public JsonResult<BackfillConfig> getBackfill( @RequestParam("id")Integer id) {
        return JsonResult.success(dataService.getBackfill(id));
    }

    @ApiOperation("删除表单回填配置")
    @GetMapping("/delete-backfill")
    public JsonResult<Integer> deleteBackfill(@RequestParam("id")Integer id) {
        return JsonResult.success(dataService.deleteBackfill(id));
    }

    @GetMapping("/get-resource-value")
    @ApiOperation(value = "获取引用实体名称字段数据集合")
    public JsonResult<List<Map<String, Object>>> getResourceFieldValue(BackfillParam backfillParam){
        return JsonResult.success(dataService.getResourceFieldValue(backfillParam));
    }

}
