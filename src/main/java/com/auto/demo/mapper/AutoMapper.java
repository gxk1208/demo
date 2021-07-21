package com.auto.demo.mapper;


import com.auto.demo.base.BaseMapper;
import com.auto.demo.entity.Auto;
import com.auto.demo.param.AutoArrayParam;
import com.auto.demo.param.AutoObjectParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AutoMapper extends BaseMapper<Auto> {
    Integer addObject(@Param("auto") AutoObjectParam auto);

    Integer addArray(@Param("auto")AutoArrayParam auto);

    List<AutoObjectParam> pageObject();

    List<AutoArrayParam> pageArray();
}
