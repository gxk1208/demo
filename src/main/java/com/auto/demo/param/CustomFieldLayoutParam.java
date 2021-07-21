package com.auto.demo.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/6/17 13:31
 */
@Data
public class CustomFieldLayoutParam {
    /**
     * 主键标识
     */
    @ApiModelProperty(value ="主键标识", required = true)
    @ColumnType(jdbcType= JdbcType.INTEGER)
    private Integer id;

    /**
     * 字段名称
     */
    @ApiModelProperty(value ="字段名称", required = true)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String name;

    /**
     * 是否必填，0必填 1非必填
     */
    @ApiModelProperty(value ="是否必填，0必填 1非必填", required = true)
    @ColumnType(jdbcType=JdbcType.BIT)
    private Boolean nullable;

    /**
     * 是否布局，0未布局 1已布局
     */
    @ApiModelProperty(value ="是否布局，0未布局 1已布局" )
    @ColumnType(jdbcType= JdbcType.BIT)
    private Boolean isbuild;
}
