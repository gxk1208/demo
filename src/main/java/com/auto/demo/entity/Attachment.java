package com.auto.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "attachment")
@ApiModel(description = "Attachment（）")
public class Attachment implements Serializable {
    /**
     * 主键标识
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "id", insertable = false, updatable = false)
    @ApiModelProperty(value ="主键标识", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer id;

    /**
     * 所属实体
     */
    @Column(name = "entity_id")
    @ApiModelProperty(value ="所属实体", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer entityId;

    /**
     * 所属字段
     */
    @Column(name = "field_id")
    @ApiModelProperty(value ="所属字段", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer fieldId;

    /**
     * 所属数据
     */
    @Column(name = "data_id")
    @ApiModelProperty(value ="所属数据", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer dataId;

    /**
     * 附件路径
     */
    @ApiModelProperty(value ="附件路径", required = true)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String path;

    /**
     * 附件名称
     */
    @ApiModelProperty(value ="附件名称", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String name;

    /**
     * 附件类型
     */
    @ApiModelProperty(value ="附件类型", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String type;

    /**
     * in bytes
     */
    @ApiModelProperty(value ="in bytes", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer size;

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
     * 获取所属实体
     *
     * @return entity_id - 所属实体
     */
    public Integer getEntityId() {
        return entityId;
    }

    /**
     * 设置所属实体
     *
     * @param entityId 所属实体
     */
    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    /**
     * 获取所属字段
     *
     * @return field_id - 所属字段
     */
    public Integer getFieldId() {
        return fieldId;
    }

    /**
     * 设置所属字段
     *
     * @param fieldId 所属字段
     */
    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * 获取所属数据
     *
     * @return data_id - 所属数据
     */
    public Integer getDataId() {
        return dataId;
    }

    /**
     * 设置所属数据
     *
     * @param dataId 所属数据
     */
    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    /**
     * 获取附件路径
     *
     * @return path - 附件路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置附件路径
     *
     * @param path 附件路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取附件名称
     *
     * @return name - 附件名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置附件名称
     *
     * @param name 附件名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取附件类型
     *
     * @return type - 附件类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置附件类型
     *
     * @param type 附件类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取in bytes
     *
     * @return size - in bytes
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 设置in bytes
     *
     * @param size in bytes
     */
    public void setSize(Integer size) {
        this.size = size;
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
