package com.auto.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/3/26 10:42
 */
@Data
public class IotSceneUpdStatusDateDto {
    @ApiModelProperty(value = "时间")
    private long updTime;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
