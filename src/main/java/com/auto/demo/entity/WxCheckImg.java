package com.auto.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/1/15 10:33
 */
@Data
@ApiModel(description = "校验图片")
public class WxCheckImg {

    @ApiModelProperty(value = "要检测的多媒体url")
    private String media_url;

    @ApiModelProperty(value = "1:音频;2:图片")
    private Integer media_type;

    private String appId;

}
