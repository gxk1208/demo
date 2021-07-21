package com.auto.demo.param;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/3/19 9:23
 */
@Data
public class AutoObjectParam {
    private Integer id;
    private String name;
    private JSONObject selfData;
    private Object objectData;
    private String stringData;
}


