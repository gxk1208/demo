package com.auto.demo.dto;

import com.auto.demo.entity.SelfEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/5/25 8:41
 */
@Data
@ApiModel(description = "明细实体")
public class SelfEntityDto extends SelfEntity {
    @ApiModelProperty(value ="明细实体主键标识", required = false)
    private SelfEntity detailEntity;
    @ApiModelProperty(value ="实体类型描述", required = false)
    private String typeDesc;
}
