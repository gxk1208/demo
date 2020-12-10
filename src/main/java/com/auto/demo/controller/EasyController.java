package com.auto.demo.controller;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.auto.demo.common.JsonResult;
import com.auto.demo.service.EasyService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/9/17 15:53
 */
@RestController
@RequestMapping("/easy")
@Api("测试")
@Slf4j
public class EasyController {

    @Autowired
    private EasyService easyService;

    @GetMapping("/")
    public JsonResult<String> test(){
        return JsonResult.success(easyService.test());
    }

    @GetMapping("/img-code")
    public JsonResult<String> imgCode(HttpServerRequest request, HttpServerResponse response){

        return JsonResult.success(easyService.imgCode(request,response));
    }

}
