package com.auto.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.auto.demo.common.PageParam;
import com.auto.demo.common.PagedList;
import com.auto.demo.entity.Auto;
import com.auto.demo.mapper.AutoMapper;
import com.auto.demo.param.AutoArrayParam;
import com.auto.demo.param.AutoObjectParam;
import com.auto.demo.service.AutoService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Integer addObject(AutoObjectParam auto) {
        autoMapper.addObject(auto);
        return auto.getId();
    }

    @Override
    public Integer addArray(AutoArrayParam auto) {
        autoMapper.addArray(auto);
        return auto.getId();
    }

    @Override
    public PagedList<AutoObjectParam> pageObject(PageParam page) {
        PageHelper.startPage(page.getPage(), page.getPageSize());
        List<AutoObjectParam> autos = autoMapper.pageObject();
        autos.forEach( a -> {
            String str = a.getStringData().substring(0, 1);
            if(str.equals("{")){
                a.setSelfData(JSON.parseObject(a.getStringData()));
            }
        });
        return PagedList.parse(autos);
    }

    @Override
    public PagedList<AutoArrayParam> pageArray(PageParam page) {
        PageHelper.startPage(page.getPage(), page.getPageSize());
        List<AutoArrayParam> autos = autoMapper.pageArray();
        autos.forEach( a -> {
            String str = a.getStringData().substring(0, 1);
            if(str.equals("[")){
                a.setSelfData(JSON.parseArray(a.getStringData()));
            }
        });
        return PagedList.parse(autos);
    }

    @Override
    public PagedList<Auto> page1(PageParam page) {
        PageHelper.startPage(page.getPage(), page.getPageSize());
        List<Auto> autos = autoMapper.selectAll();
        return PagedList.parse(autos);
    }
}
