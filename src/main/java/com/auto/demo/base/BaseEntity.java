package com.auto.demo.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.auto.demo.utils.JsonDateSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Column;
import java.util.Date;

@Data
public class BaseEntity {
    public BaseEntity() {

    }

    public void init(Integer id, BaseEnum baseEnum) {
        this.updateBy = id;
        this.updateTime = new Date();
        if (baseEnum.ordinal() == 0) {
            this.createBy = this.updateBy;
            this.createTime = this.updateTime;
        }
    }

    public void init(Integer id, Date date, BaseEnum baseEnum) {
        this.updateBy = id;
        this.updateTime = date;
        if (baseEnum.ordinal() == 0) {
            this.createBy = this.updateBy;
            this.createTime = this.updateTime;
        }
    }

    /**
     * 更新日期
     */
    @Column(name = "update_time")
    @ApiModelProperty(value = "更新日期", required = false)
    @ColumnType(jdbcType = JdbcType.BIGINT)
    @JSONField(serializeUsing = JsonDateSerializer.class)
    private Date updateTime;

    /**
     * 更新人
     */
    @Column(name = "update_by")
    @ApiModelProperty(value = "更新人", required = false)
    @ColumnType(jdbcType = JdbcType.INTEGER)
    private Integer updateBy;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建日期", required = false)
    @ColumnType(jdbcType = JdbcType.BIGINT)
    @JSONField(serializeUsing = JsonDateSerializer.class)
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    @ApiModelProperty(value = "创建人", required = false)
    @ColumnType(jdbcType = JdbcType.INTEGER)
    private Integer createBy;

    public enum BaseEnum {
        //定义一个枚举
        INSERT, UPDATE, DELETE;
    }
}
