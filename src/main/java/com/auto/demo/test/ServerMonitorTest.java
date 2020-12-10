package com.auto.demo.test;

import com.alibaba.fastjson.JSON;
import com.auto.demo.entity.ServerMonitor;
import springfox.documentation.spring.web.json.Json;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/12/3 13:47
 */
public class ServerMonitorTest {
    public static void main(String[] args) {
        ServerMonitor serverMonitor = new ServerMonitor();
        serverMonitor.copyTo();
        Map<String, Object> map = new HashMap<>();
        map.put("server",serverMonitor);
        System.out.println(JSON.toJSONString(serverMonitor));
    }
}
