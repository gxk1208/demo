package com.hlink.saas.dto.wechat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/1/15 13:52
 */
@Data
@ApiModel(description = "校验图片异步返回值")
public class WxCheckImgAsyncDto {
    @ApiModelProperty(value = "小程序的username")
    private String ToUserName;

    @ApiModelProperty(value = "平台推送服务UserName")
    private String FromUserName;

    @ApiModelProperty(value = "发送时间")
    private String CreateTime;

    @ApiModelProperty(value = "默认为：Event")
    private String MsgType;

    @ApiModelProperty(value = "默认为：wxa_media_check")
    private String Event;

    @ApiModelProperty(value = "检测结果，0：暂未检测到风险，1：风险")
    private String isrisky;

    @ApiModelProperty(value = "附加信息，默认为空")
    private String extra_info_json;

    @ApiModelProperty(value = "小程序的appid")
    private String appid;

    @ApiModelProperty(value = "任务id")
    private String trace_id;

    @ApiModelProperty(value = "默认为：0，4294966288(-1008)为链接无法下载")
    private String status_code;

}
