package com.auto.demo.dto;

import com.auto.demo.entity.Attachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gxk
 * @version 1.0
 * @date 2020/6/8 18:12
 */
@Data
@ApiModel(description = "返回附件对象")
public class AttachmentDto extends Attachment {
    @ApiModelProperty("所属实体名称")
    private String entityName;

    @ApiModelProperty("发布时间至今")
    private String time;

    @ApiModelProperty("所属人员名称")
    private String username;
}
