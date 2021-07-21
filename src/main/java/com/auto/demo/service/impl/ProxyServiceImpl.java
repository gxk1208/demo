package com.auto.demo.service.impl;

import com.auto.demo.entity.SelfField;
import com.auto.demo.service.ProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/5/18 9:22
 */
@Service
public class ProxyServiceImpl implements ProxyService {

    @Override
    public int update() {
        System.out.println("更新成功");
        return 1;
    }

    @Override
    public int add() {
        System.out.println("新增成功");
        return 1;
    }
}
