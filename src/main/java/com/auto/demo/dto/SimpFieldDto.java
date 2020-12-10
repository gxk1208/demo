package com.auto.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/6/3 20:01
 */
@Data
public class SimpFieldDto {

    /**
     * 主键标识
     */
    @ApiModelProperty(value ="主键标识", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer id;

    /**
     * 字段名称
     */
    @ApiModelProperty(value ="字段名称", required = true)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String name;

    /**
     * 是否显示，0不显示 1显示
     */
    @ApiModelProperty(value ="是否显示，0不显示 1显示", required = false)
    @ColumnType(jdbcType= JdbcType.BIT)
    private Boolean isVisible;
}
