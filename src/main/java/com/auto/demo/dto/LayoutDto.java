package com.auto.demo.dto;


import com.auto.demo.entity.SelfField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/6/2 8:23
 */
@Data
@ApiModel(description = "用户新增编辑数据时的表单布局")
public class LayoutDto extends SelfField implements Serializable {
    /**
     * 是否占据一行，0不是，1是
     */
    @ApiModelProperty(value ="是否占据一行，0不是，1是", required = false)
    private Boolean isfull;

    /**
     * 提示
     */
    @ApiModelProperty(value ="提示", required = false)
    private String tip;

    /**
     * 布局的字段或分栏的名称
     */
    @ApiModelProperty(value ="布局的字段或分栏的名称", required = false)
    private String lname;


    @ApiModelProperty(value ="特殊字段的额外属性，例如列表的下拉选项。普通字段返回为null", required = false)
    private Object value;

}
