package com.auto.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "form_layout")
@ApiModel(description = "FormLayout（）")
public class FormLayout implements Serializable {
    /**
     * 表单布局主键标识
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id", insertable = false, updatable = false)
    @ApiModelProperty(value ="表单布局主键标识", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer id;

    /**
     * 实体id
     */
    @Column(name = "entity_id")
    @ApiModelProperty(value ="实体id", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer entityId;

    /**
     * 字段id
     */
    @Column(name = "field_id")
    @ApiModelProperty(value ="字段id", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer fieldId;

    /**
     * 字段名称
     */
    @ApiModelProperty(value ="字段名称", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String name;

    /**
     * 是否占据一行，0不是，1是
     */
    @ApiModelProperty(value ="是否占据一行，0不是，1是", required = false)
    @ColumnType(jdbcType=JdbcType.BIT)
    private Boolean isfull;

    /**
     * 提示
     */
    @ApiModelProperty(value ="提示", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String tip;

    /**
     * 0分栏，其他 字段本身类型
     */
    @ApiModelProperty(value ="0分栏，其他 字段本身类型", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer type;

    @Column(name = "create_by")
    @ApiModelProperty(value ="", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer createBy;

    @Column(name = "create_time")
    @ApiModelProperty(value ="", required = false)
    @ColumnType(jdbcType=JdbcType.BIGINT)
    private Long createTime;

    @Column(name = "update_by")
    @ApiModelProperty(value ="", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer updateBy;

    @Column(name = "update_time")
    @ApiModelProperty(value ="", required = false)
    @ColumnType(jdbcType=JdbcType.BIGINT)
    private Long updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * 获取表单布局主键标识
     *
     * @return id - 表单布局主键标识
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置表单布局主键标识
     *
     * @param id 表单布局主键标识
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取实体id
     *
     * @return entity_id - 实体id
     */
    public Integer getEntityId() {
        return entityId;
    }

    /**
     * 设置实体id
     *
     * @param entityId 实体id
     */
    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    /**
     * 获取字段id
     *
     * @return field_id - 字段id
     */
    public Integer getFieldId() {
        return fieldId;
    }

    /**
     * 设置字段id
     *
     * @param fieldId 字段id
     */
    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
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

    /**
     * 获取是否占据一行，0不是，1是
     *
     * @return isfull - 是否占据一行，0不是，1是
     */
    public Boolean getIsfull() {
        return isfull;
    }

    /**
     * 设置是否占据一行，0不是，1是
     *
     * @param isfull 是否占据一行，0不是，1是
     */
    public void setIsfull(Boolean isfull) {
        this.isfull = isfull;
    }

    /**
     * 获取提示
     *
     * @return tip - 提示
     */
    public String getTip() {
        return tip;
    }

    /**
     * 设置提示
     *
     * @param tip 提示
     */
    public void setTip(String tip) {
        this.tip = tip;
    }

    /**
     * 获取0分栏，其他 字段本身类型
     *
     * @return type - 0分栏，其他 字段本身类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置0分栏，其他 字段本身类型
     *
     * @param type 0分栏，其他 字段本身类型
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return create_by
     */
    public Integer getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    /**
     * @return create_time
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_by
     */
    public Integer getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     */
    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * @return update_time
     */
    public Long getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
}
