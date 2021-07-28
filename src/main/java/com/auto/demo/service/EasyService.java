package com.auto.demo.service;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;

import java.util.Map;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/9/17 15:54
 */
public interface EasyService {
    String test();

    String imgCode(HttpServerRequest request, HttpServerResponse response);

    Integer repeat(String message, Integer tenantId);

    void topicTest(String message);

    Map<String, String> easyMap(Object test);

}
