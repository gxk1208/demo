package com.auto.demo.entity;

import lombok.Data;

@Data
public class FlowDataFilter {
    String field;
    String fieldDisplay;
    String op;//LK
    String tableName;
}
