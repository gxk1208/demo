package com.auto.demo.proxy;

import com.auto.demo.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Proxy;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/5/18 9:23
 */
@Service(value = "proxy")
public class ProxyServiceProxy implements ProxyService {

    @Autowired
    private ProxyService proxyService;

    @Override
    public int update() {
        System.out.println("代理");
        return proxyService.update();
    }

    @Override
    public int add() {
        return 0;
    }
}
