package com.auto.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "charge_rule")
@ApiModel(description = "ChargeRule（）")
public class ChargeRule implements Serializable {
    /**
     * test收费自定义规则主键标识
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id", insertable = false, updatable = false)
    @ApiModelProperty(value ="test收费自定义规则主键标识", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer id;

    /**
     * 规则（数字是字段id）
     */
    @ApiModelProperty(value ="规则（数字是字段id）", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String rule;

    private static final long serialVersionUID = 1L;

    /**
     * 获取test收费自定义规则主键标识
     *
     * @return id - test收费自定义规则主键标识
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置test收费自定义规则主键标识
     *
     * @param id test收费自定义规则主键标识
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取规则（数字是字段id）
     *
     * @return rule - 规则（数字是字段id）
     */
    public String getRule() {
        return rule;
    }

    /**
     * 设置规则（数字是字段id）
     *
     * @param rule 规则（数字是字段id）
     */
    public void setRule(String rule) {
        this.rule = rule;
    }
}
