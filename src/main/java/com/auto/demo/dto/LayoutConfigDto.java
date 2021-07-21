package com.auto.demo.dto;

import com.auto.demo.entity.LayoutConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/6/5 10:52
 */
@Data
@ApiModel(description = "布局对象")
public class LayoutConfigDto extends LayoutConfig {

    @ApiModelProperty(value ="字段集合", required = false)
    private List<Object> objectList;

    @ApiModelProperty(value ="共享状态描述", required = false)
    private String shareDesc;

}
