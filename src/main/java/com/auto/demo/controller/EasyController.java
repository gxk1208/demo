package com.auto.demo.controller;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.auto.demo.common.JsonResult;
import com.auto.demo.mq.consumer.TopicTest;
import com.auto.demo.service.EasyService;
import com.auto.demo.service.ProxyService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    @Resource(name = "proxy")
    private ProxyService proxyService;

    @GetMapping("/")
    public JsonResult<String> test(){
        return JsonResult.success(easyService.test());
    }

    @GetMapping("/img-code")
    public JsonResult<String> imgCode(HttpServerRequest request, HttpServerResponse response){
        return JsonResult.success(easyService.imgCode(request,response));
    }

    @GetMapping("/topic")
    public JsonResult<Void> topicTest(@RequestParam(value = "message") String message,@RequestParam(value = "tenantId") Integer tenantId){
        TopicTest.send(message,tenantId);
        return JsonResult.success();
    }

    @GetMapping("/repeat")
    public JsonResult<Integer> repeat(@RequestParam(value = "serialNumber") String serialNumber){
        return JsonResult.success(easyService.repeat(serialNumber,1));
    }

    @GetMapping("/proxy")
    public JsonResult<Integer> proxy(){
        return JsonResult.success(proxyService.update());
    }

}
