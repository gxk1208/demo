package com.auto.demo.param;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.annotation.security.DenyAll;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/3/19 11:05
 */
@Data
public class AutoArrayParam {
    private Integer id;
    private String name;
    private JSONArray selfData;
    private Object objectData;
    private String stringData;
}
