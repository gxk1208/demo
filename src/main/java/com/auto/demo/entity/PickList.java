package com.auto.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "pick_list")
@ApiModel(description = "PickList（）")
public class PickList implements Serializable {
    /**
     * 主键标识
     */
    @Id
    @Column(name = "id", updatable = false)
    @ApiModelProperty(value ="主键标识", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer id;

    /**
     * 实体id
     */
    @Column(name = "entity_id")
    @ApiModelProperty(value ="实体id", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer entityId;

    /**
     * 字段id
     */
    @Column(name = "field_id")
    @ApiModelProperty(value ="字段id", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer fieldId;

    /**
     * 列表选择项名称
     */
    @ApiModelProperty(value ="列表选择项名称", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String name;

    /**
     * 是否禁用0-未禁用，1-已禁用
     */
    @Column(name = "is_active")
    @ApiModelProperty(value ="是否禁用0-未禁用，1-已禁用", required = true)
    @ColumnType(jdbcType=JdbcType.BIT)
    private Boolean isActive;

    /**
     * 是否删除0-未删除，1-已删除
     */
    @Column(name = "delete_flag")
    @ApiModelProperty(value ="是否删除0-未删除，1-已删除", required = false)
    @ColumnType(jdbcType=JdbcType.BIT)
    private Boolean deleteFlag;

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

    @Column(name = "change_time")
    @ApiModelProperty(value ="", required = false)
    @ColumnType(jdbcType=JdbcType.DATE)
    private Date changeTime;

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
     * 获取列表选择项名称
     *
     * @return name - 列表选择项名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置列表选择项名称
     *
     * @param name 列表选择项名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取是否禁用0-未禁用，1-已禁用
     *
     * @return is_active - 是否禁用0-未禁用，1-已禁用
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * 设置是否禁用0-未禁用，1-已禁用
     *
     * @param isActive 是否禁用0-未禁用，1-已禁用
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * 获取是否删除0-未删除，1-已删除
     *
     * @return delete_flag - 是否删除0-未删除，1-已删除
     */
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置是否删除0-未删除，1-已删除
     *
     * @param deleteFlag 是否删除0-未删除，1-已删除
     */
    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
