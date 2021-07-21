package com.auto.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "auto")
@ApiModel(description = "Auto（）")
public class Auto implements Serializable {
    /**
     * 测试表主键标识
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id", insertable = false, updatable = false)
    @ApiModelProperty(value ="测试表主键标识", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value ="名称", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String name;

    @Column(name = "self_data")
    @ApiModelProperty(value ="", required = false)
    private String selfData;

    private static final long serialVersionUID = 1L;

    /**
     * 获取测试表主键标识
     *
     * @return id - 测试表主键标识
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置测试表主键标识
     *
     * @param id 测试表主键标识
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return self_data
     */
    public String getSelfData() {
        return selfData;
    }

    /**
     * @param selfData
     */
    public void setSelfData(String selfData) {
        this.selfData = selfData;
    }
}
