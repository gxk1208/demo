package com.auto.demo.controller;

import com.auto.demo.common.JsonResult;
import com.auto.demo.entity.Auto;
import com.auto.demo.service.AutoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = "/add")
    public JsonResult<Integer> add(@RequestBody Auto auto){
        return JsonResult.success(autoService.add(auto));
    }
}
