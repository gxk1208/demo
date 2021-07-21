package com.auto.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "charge_field")
@ApiModel(description = "ChargeField（）")
public class ChargeField implements Serializable {
    /**
     * test收费自定义字段主键标识
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id", insertable = false, updatable = false)
    @ApiModelProperty(value ="test收费自定义字段主键标识", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer id;

    /**
     * 入参
     */
    @Column(name = "in_param")
    @ApiModelProperty(value ="入参", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String inParam;

    /**
     * 字段含义
     */
    @ApiModelProperty(value ="字段含义", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String mean;

    private static final long serialVersionUID = 1L;

    /**
     * 获取test收费自定义字段主键标识
     *
     * @return id - test收费自定义字段主键标识
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置test收费自定义字段主键标识
     *
     * @param id test收费自定义字段主键标识
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取入参
     *
     * @return in_param - 入参
     */
    public String getInParam() {
        return inParam;
    }

    /**
     * 设置入参
     *
     * @param inParam 入参
     */
    public void setInParam(String inParam) {
        this.inParam = inParam;
    }

    /**
     * 获取字段含义
     *
     * @return mean - 字段含义
     */
    public String getMean() {
        return mean;
    }

    /**
     * 设置字段含义
     *
     * @param mean 字段含义
     */
    public void setMean(String mean) {
        this.mean = mean;
    }
}
