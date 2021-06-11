package com.auto.demo.controller;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;
import com.alibaba.fastjson.JSON;
import com.auto.demo.common.JsonResult;
import com.auto.demo.dto.KTCarFeeReportDto;
import com.auto.demo.dto.KTCarFeeReportParam;
import com.auto.demo.dto.KetoPostPayReport;
import com.auto.demo.mq.consumer.FanoutTest;
import com.auto.demo.service.EasyService;
import com.auto.demo.service.ProxyService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import okhttp3.Request;
import javax.annotation.Resource;
import java.util.ArrayList;

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

    private okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");

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

    @GetMapping("/fanout")
    public JsonResult<Void> fanoutTest(@RequestParam(value = "message") String message,@RequestParam(value = "tenantId") Integer tenantId){
        FanoutTest.send(message,tenantId);
        return JsonResult.success();
    }

    @GetMapping("/topic")
    public JsonResult<Void> topicTest(@RequestParam(value = "message") String message){
        easyService.topicTest(message);
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

    @PostMapping("/keto/PostPayFeeInfo")
    public Integer postPayFeeInfo(@RequestBody KetoPostPayReport ketoPostPayReport) throws Exception {
        log.info("科拓缴费记录上报ketoPostPayReport====={}", ketoPostPayReport);

        // 发送给saas平台
        KTCarFeeReportParam reportParam = new KTCarFeeReportParam();
        ArrayList<KTCarFeeReportDto> params = new ArrayList<>();
        KTCarFeeReportDto param = new KTCarFeeReportDto();
        param.setAdapterId(36);
        param.setPayTime(ketoPostPayReport.getPayTime());
        param.setEntryTime(ketoPostPayReport.getEntryTime());
        param.setPaidMoney(ketoPostPayReport.getPaidMoney());
        param.setPayMethod(ketoPostPayReport.getPayMethod());
        param.setPlateNo(ketoPostPayReport.getPlateNo());
        params.add(param);
        reportParam.setReportDtos(params);
        // String url = "http://172.16.251.18:8099/hlink-saas-adapter/api/pms/PostPayFeeInfo";
        String url = "https://saas.hjt.link/hlink-saas-adapter/api/pms/PostPayFeeInfo";
        String json = JSON.toJSONString(reportParam);
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, json);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            log.info("saas 缴费上报返回值 {}",res);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }
}
