package com.auto.demo.service;

import com.auto.demo.common.PageParam;
import com.auto.demo.common.PagedList;
import com.auto.demo.entity.Auto;
import com.auto.demo.param.AutoArrayParam;
import com.auto.demo.param.AutoObjectParam;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/3/18 17:58
 */
public interface AutoService {
    Integer addObject(AutoObjectParam auto);

    Integer addArray(AutoArrayParam auto);

    PagedList<AutoObjectParam> pageObject(PageParam page);

    PagedList<AutoArrayParam> pageArray(PageParam page);


    PagedList<Auto> page1(PageParam page);

}
