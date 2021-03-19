package com.auto.demo.service.impl;

import com.auto.demo.mapper.AutoMapper;
import com.auto.demo.entity.Auto;
import com.auto.demo.service.AutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/3/18 17:58
 */
@Service
public class AutoServiceImpl implements AutoService {

    @Autowired
    private AutoMapper autoMapper;

    @Override
    public Integer add(Auto auto) {
        return autoMapper.insert(auto);
    }

}
