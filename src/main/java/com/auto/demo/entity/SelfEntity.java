package com.auto.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "self_entity")
@ApiModel(description = "SelfEntity（）")
public class SelfEntity implements Serializable {
    /**
     * 主键标识
     */
    @Id
    @Column(name = "id", updatable = false)
    @ApiModelProperty(value ="主键标识", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer id;

    @Column(name = "parent_id", updatable = false)
    @ApiModelProperty(value ="父级实体id 未勾选明细实体：0，勾选明细实体：实体id", required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer parentId;

    /**
     * 实体名称
     */
    @ApiModelProperty(value ="实体名称", required = true)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String name;

    /**
     * 名称字段
     */
    @ApiModelProperty(value ="名称字段 未勾选明细实体：0，勾选明细实体：实体id", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String nameField;
    /**
     * 实体拼音名称
     */
    @ApiModelProperty(value ="实体拼音名称", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String pname;

    /**
     * 注解
     */
    @ApiModelProperty(value ="注解", required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String comment;

    @ApiModelProperty(value ="实体类型", required = true)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer type;



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
     * 获取实体名称
     *
     * @return name - 实体名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置实体名称
     *
     * @param name 实体名称
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
     * 获取注解
     *
     * @return comment - 注解
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置注解
     *
     * @param comment 注解
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SelfEntity that = (SelfEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(parentId, that.parentId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(nameField, that.nameField) &&
                Objects.equals(pname, that.pname) &&
                Objects.equals(comment, that.comment) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentId, name, nameField, pname, comment, type);
    }
}
