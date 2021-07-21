package com.auto.demo.dto;

import lombok.Data;

import java.util.Map;

@Data
public class SelfWorkFlowDto {
    Integer entityId;
    /**
     * 用户流程中，（字段key，字段值)
     * */
    Map<String, Object> filedValueMap;
}
