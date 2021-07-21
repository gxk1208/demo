package com.auto.demo.controller;

import com.auto.demo.common.JsonResult;
import com.auto.demo.dto.SelfEntityDto;
import com.auto.demo.entity.SelfEntity;
import com.auto.demo.service.EntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/5/19 14:02
 */
@RestController
@RequestMapping("/entity")
@Api("实体操作")
@Slf4j
public class EntityController {
    @Autowired
    private EntityService entityService;

    @PostMapping("/add")
    @ApiOperation(value = "新增实体")
    public JsonResult<Integer> addEntity(@RequestBody SelfEntity entity){
        return JsonResult.success(entityService.add(entity));
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改实体")
    public JsonResult<Integer> updateEntity(@RequestBody SelfEntity entity){
        return JsonResult.success(entityService.updateEntity(entity));
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除实体")
    public JsonResult<Integer> deleteEntity(@RequestParam("id") Integer id){
        return JsonResult.success(entityService.deleteEntity(id));
    }

    @GetMapping("/get-all")
    @ApiOperation(value = "获取所有实体")
    public JsonResult<List<SelfEntityDto>> getAllEntity(){
        return JsonResult.success(entityService.getAllEntity());
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取指定实体")
    public JsonResult<SelfEntityDto> getEntity(@RequestParam("id") Integer id){
        return JsonResult.success(entityService.getEntity(id));
    }

    @GetMapping("/get-master")
    @ApiOperation(value = "获取可以被明细实体绑定的实体")
    public JsonResult<List<SelfEntity>> getMasterEntity(){
        return JsonResult.success(entityService.getMasterEntity());
    }

    @GetMapping("/get-atta-entity")
    @ApiOperation(value = "获取含有附件类型字段的的实体")
    public JsonResult<List<SelfEntity>> getAttaEntity(){
        return JsonResult.success(entityService.getAttaEntity());
    }


}
