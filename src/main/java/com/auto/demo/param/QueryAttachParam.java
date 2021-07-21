package com.auto.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/6/8 17:00
 */
@ApiModel(description = "查询附件搜索参数")
@Data
public class QueryAttachParam {
    @ApiModelProperty("实体标识")
    private Integer entityId;
    @ApiModelProperty("搜索参数")
    private String search;
    @ApiModelProperty("排序")
    private Integer sort;
}
