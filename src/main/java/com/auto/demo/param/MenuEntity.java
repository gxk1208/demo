package com.auto.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/6/5 17:04
 */
@Data
@ApiModel(description = "菜单布局参数")
public class MenuEntity {

    @ApiModelProperty(value ="主键标识", required = false)
    private Integer id;

    @ApiModelProperty(value ="1实体 2父级 3文件", required = true)
    private Integer type;

    @ApiModelProperty(value ="名称", required = true)
    private String name;

    @ApiModelProperty(value ="图标", required = true)
    private String icon;

}
