package com.auto.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/9/18 14:20
 */
@Data
@ApiModel(description = "包含02")
public class AnyContains02 {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "id")
    private AnyContains01 ac01;

}
