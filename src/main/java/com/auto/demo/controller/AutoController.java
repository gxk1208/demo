package com.auto.demo.controller;

import com.auto.demo.common.JsonResult;
import com.auto.demo.common.PageParam;
import com.auto.demo.common.PagedList;
import com.auto.demo.entity.Auto;
import com.auto.demo.param.AutoArrayParam;
import com.auto.demo.param.AutoObjectParam;
import com.auto.demo.service.AutoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/3/18 17:57
 */
@Api(tags = "测试postgresql")
@RestController
@RequestMapping(value = "/pg-auto")
public class AutoController {

    @Autowired
    private AutoService autoService;

    @PostMapping(value = "/add-object")
    @ApiOperation(value = "添加")
    public JsonResult<Integer> addObject(@RequestBody AutoObjectParam auto){
        return JsonResult.success(autoService.addObject(auto));
    }

    @PostMapping(value = "/add-array")
    @ApiOperation(value = "添加")
    public JsonResult<Integer> add(@RequestBody AutoArrayParam auto){
        return JsonResult.success(autoService.addArray(auto));
    }


    @GetMapping(value = "/page-object")
    @ApiOperation(value = "分页")
    public JsonResult<PagedList<AutoObjectParam>> pageObject(PageParam page){
        return JsonResult.success(autoService.pageObject(page));
    }

    @GetMapping(value = "/page-array")
    @ApiOperation(value = "分页")
    public JsonResult<PagedList<AutoArrayParam>> pageArray(PageParam page){
        return JsonResult.success(autoService.pageArray(page));
    }

    @GetMapping(value = "/page1")
    @ApiOperation(value = "分页")
    public JsonResult<PagedList<Auto>> page1(PageParam page){
        return JsonResult.success(autoService.page1(page));
    }
}
