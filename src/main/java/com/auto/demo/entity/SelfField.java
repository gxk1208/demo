package com.auto.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "self_field")
@ApiModel(description = "SelfField（）")
public class SelfField implements Serializable {
    /**
     * 主键标识
     */
    @Id
    @Column(name = "id", updatable = false)
    @ApiModelProperty(value ="主键标识", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer id;

    /**
     * 实体表标识
     */
    @Column(name = "entity_id")
    @ApiModelProperty(value ="实体表标识", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer entityId;

    /**
     * 字段名称
     */
    @ApiModelProperty(value ="字段名称", required = true)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String name;

    /**
     * 字段拼音名称
     */
    @ApiModelProperty(value ="字段拼音名称", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String pname;

    /**
     * 字段注解
     */
    @ApiModelProperty(value ="字段注解", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String comment;

    /**
     * 字段默认值
     */

    @Column(name = "default_value")
    @ApiModelProperty(value ="字段默认值", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String defaultValue;

    /**
     * 字段展示类型
     */
    @ApiModelProperty(value ="字段展示类型", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String displayType;

    /**
     * 特殊字段额外值
     */
    @ApiModelProperty(value ="特殊字段额外值", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String extraValue;

    /**
     * 字段类型
     */
    @ApiModelProperty(value ="字段类型", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer type;

    /**
     * 字段排序
     */
    @ApiModelProperty(value ="字段排序", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer sort;

    /**
     * 是否布局，0未布局 1已布局
     */
    @ApiModelProperty(value ="是否布局，0未布局 1已布局", required = false)
    @ColumnType(jdbcType=JdbcType.BIT)
    private Boolean isBuild;

    /**
     * 是否显示，0不显示 1显示
     */
    @ApiModelProperty(value ="是否显示，0不显示 1显示", required = false)
    @ColumnType(jdbcType=JdbcType.BIT)
    private Boolean isVisible;

    /**
     * 是否可以为null，0不可以 1可以
     */
    @ApiModelProperty(value ="是否可以为null，0不可以 1可以", required = false)
    @ColumnType(jdbcType=JdbcType.BIT)
    private Boolean nullable;

    /**
     * 是否可以修改，0不可以 1可以
     */
    @ApiModelProperty(value ="是否可以修改，0不可以 1可以", required = false)
    @ColumnType(jdbcType=JdbcType.BIT)
    private Boolean updateable;

    /**
     * 是否可以重复，0不可以 1可以
     */
    @ApiModelProperty(value ="是否可以重复，0不可以 1可以", required = false)
    @ColumnType(jdbcType=JdbcType.BIT)
    private Boolean repeatable;

    /**
     * 字段创建时间 0建表时创建 1建表后添加
     */
    @ApiModelProperty(value ="字段创建时间 0建表时创建 1建表后添加", required = false)
    @ColumnType(jdbcType=JdbcType.BIT)
    private Boolean createType;

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
     * 获取实体表标识
     *
     * @return entity_id - 实体表标识
     */
    public Integer getEntityId() {
        return entityId;
    }

    /**
     * 设置实体表标识
     *
     * @param entityId 实体表标识
     */
    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    /**
     * 获取字段名称
     *
     * @return name - 字段名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置字段名称
     *
     * @param name 字段名称
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * 获取字段注解
     *
     * @return comment - 字段注解
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置字段注解
     *
     * @param comment 字段注解
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 获取字段默认值
     *
     * @return default_value - 字段默认值
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * 设置字段默认值
     *
     * @param defaultValue 字段默认值
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * 获取字段类型
     *
     * @return type - 字段类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置字段类型
     *
     * @param type 字段类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取是否可以为null，0可以 1不可以
     *
     * @return nullable - 是否可以为null，0可以 1不可以
     */
    public Boolean getNullable() {
        return nullable;
    }

    /**
     * 设置是否可以为null，0可以 1不可以
     *
     * @param nullable 是否可以为null，0可以 1不可以
     */
    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public Boolean getUpdateable() {
        return updateable;
    }

    public void setUpdateable(Boolean updateable) {
        this.updateable = updateable;
    }

    public Boolean getRepeatable() {
        return repeatable;
    }

    public void setRepeatable(Boolean repeatable) {
        this.repeatable = repeatable;
    }

    public Boolean getCreateType() {
        return createType;
    }

    public void setCreateType(Boolean createType) {
        this.createType = createType;
    }

    public String getExtraValue() {
        return extraValue;
    }

    public void setExtraValue(String extraValue) {
        this.extraValue = extraValue;
    }

    public Boolean getIsBuild() {
        return isBuild;
    }

    public void setIsBuild(Boolean isBuild) {
        this.isBuild = isBuild;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
