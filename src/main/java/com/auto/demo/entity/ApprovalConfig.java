package com.auto.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "approval_config")
@ApiModel(description = "approval_config")
@Data
public class ApprovalConfig implements Serializable {
    @Id
    @Column(name = "id", updatable = false)
    @ApiModelProperty(value ="流程id",required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer id;

    /**
     * 应用实体
     */
    @Column(name = "belong_entity")
    @ApiModelProperty(value ="应用实体",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String belongEntity;

    /**
     * 流程名称
     */
    @Column(name = "name")
    @ApiModelProperty(value ="流程名称",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String name;

    /**
     * 是否停用
     */
    @Column(name = "disable_flag")
    @ApiModelProperty(value ="是否禁用",required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer disableFlag;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    @ApiModelProperty(value ="创建人",required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer createBy;

    /**
     * 修改人
     */
    @Column(name = "update_by")
    @ApiModelProperty(value ="修改人",required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer updateBy;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    @ApiModelProperty(value ="修改时间",required = false)
    @ColumnType(jdbcType=JdbcType.BIGINT)
    private Long updateTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value ="创建时间",required = false)
    @ColumnType(jdbcType=JdbcType.BIGINT)
    private Long createTime;

    /**
     * 流程定义
     */
    @Column(name = "flow_definition")
    @ApiModelProperty(value ="流程定义",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String flowDefinition;

    @Column(name = "delete_flag")
    @ApiModelProperty(value ="删除标记",required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer deleteFlag;

}
