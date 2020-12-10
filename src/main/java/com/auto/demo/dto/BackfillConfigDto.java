package com.auto.demo.dto;

import com.auto.demo.entity.BackfillConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/6/10 10:49
 */
@Data
@ApiModel(description = "返回表单回填配置对象")
public class BackfillConfigDto extends BackfillConfig {
    @ApiModelProperty(value ="目标字段名称")
    private String targetName;

    @ApiModelProperty(value ="源字段名称")
    private String sourceName;

    @ApiModelProperty(value ="回填配置描述")
    private String configDesc;
}
