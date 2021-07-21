package com.auto.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "layout_config")
@ApiModel(description = "LayoutConfig（）")
public class LayoutConfig implements Serializable {
    /**
     * 主键标识
     */
    @Id
    @Column(name = "id", updatable = false)
    @ApiModelProperty(value ="主键标识", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer id;

    @ApiModelProperty(value ="", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String share;

    @Column(name = "belong_entity")
    @ApiModelProperty(value ="", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer belongEntity;

    @ApiModelProperty(value ="", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer type;

    @Column(name = "config_name")
    @ApiModelProperty(value ="", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String configName;

    /**
     * 是否被选中，0未选中，1选中
     */
    @ApiModelProperty(value ="是否被选中，0未选中，1选中", required = false)
    @ColumnType(jdbcType=JdbcType.BIT)
    private Boolean selected;



    /**
     * JSON格式配置
     */
    @ApiModelProperty(value ="JSON格式配置", required = true)
    @ColumnType(jdbcType=JdbcType.LONGVARCHAR)
    private String config;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键标识
     *
     * @return id - 主键标识
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键标识
     *
     * @param id 主键标识
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return share
     */
    public String getShare() {
        return share;
    }

    /**
     * @param share
     */
    public void setShare(String share) {
        this.share = share;
    }

    /**
     * @return belong_entity
     */
    public Integer getBelongEntity() {
        return belongEntity;
    }

    /**
     * @param belongEntity
     */
    public void setBelongEntity(Integer belongEntity) {
        this.belongEntity = belongEntity;
    }

    /**
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return config_name
     */
    public String getConfigName() {
        return configName;
    }

    /**
     * @param configName
     */
    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    /**
     * 获取JSON格式配置
     *
     * @return config - JSON格式配置
     */
    public String getConfig() {
        return config;
    }

    /**
     * 设置JSON格式配置
     *
     * @param config JSON格式配置
     */
    public void setConfig(String config) {
        this.config = config;
    }
}
