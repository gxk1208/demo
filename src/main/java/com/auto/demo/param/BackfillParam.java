package com.auto.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/6/10 14:18
 */
@Data
@ApiModel(description = "表单回填配置查询参数")
public class BackfillParam {
    @ApiModelProperty("实体id")
    private Integer entityId;

    @ApiModelProperty("引用字段id")
    private Integer fieldId;

    @ApiModelProperty("引用字段数据id")
    private Integer quoteId;

    @ApiModelProperty("数据id，判断修改还是新建数据")
    private Integer dataId;
}
