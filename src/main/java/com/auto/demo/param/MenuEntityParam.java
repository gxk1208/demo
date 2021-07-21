package com.auto.demo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/6/5 17:26
 */
@Data
@ApiModel(description = "菜单布局参数")
public class MenuEntityParam extends MenuEntity {
    @ApiModelProperty(value ="子参数", required = false)
    private List<MenuEntity> simpEntityDtos;
}
