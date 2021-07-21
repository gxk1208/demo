package com.auto.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/7/8 16:02
 */
@Data
public class ChargeRuleDto {

    @ApiModelProperty(value = "1字段 2定值 3运算符")
    private Integer type;

    @ApiModelProperty(value = "值")
    private String value;
}
