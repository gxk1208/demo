package com.auto.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "backfill_config")
@ApiModel(description = "BackfillConfig（）")
public class BackfillConfig implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id", insertable = false, updatable = false)
    @ApiModelProperty(value ="主键标识", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer id;

    @Column(name = "entity_id")
    @ApiModelProperty(value ="实体id", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer entityId;

    @Column(name = "field_id")
    @ApiModelProperty(value ="引用字段id", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer fieldId;

    @Column(name = "source_field")
    @ApiModelProperty(value ="源字段id", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer sourceField;

    @Column(name = "target_field")
    @ApiModelProperty(value ="目标字段id", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer targetField;

    @ApiModelProperty(value ="新增、修改、是否强制，0 选中 1 未选中 0,1,0", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String config;

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
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return entity_id
     */
    public Integer getEntityId() {
        return entityId;
    }

    /**
     * @param entityId
     */
    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    /**
     * @return field_id
     */
    public Integer getFieldId() {
        return fieldId;
    }

    /**
     * @param fieldId
     */
    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * @return source_field
     */
    public Integer getSourceField() {
        return sourceField;
    }

    /**
     * @param sourceField
     */
    public void setSourceField(Integer sourceField) {
        this.sourceField = sourceField;
    }

    /**
     * @return target_field
     */
    public Integer getTargetField() {
        return targetField;
    }

    /**
     * @param targetField
     */
    public void setTargetField(Integer targetField) {
        this.targetField = targetField;
    }

    /**
     * @return config
     */
    public String getConfig() {
        return config;
    }

    /**
     * @param config
     */
    public void setConfig(String config) {
        this.config = config;
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
