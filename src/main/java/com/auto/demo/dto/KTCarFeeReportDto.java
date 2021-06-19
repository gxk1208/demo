package com.auto.demo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/6/10 16:53
 */
@Data
public class KTCarFeeReportDto {

    @ApiModelProperty(value = "实收总金额")
    private Integer paidMoney;

    @ApiModelProperty(value = "付款方式")
    private Integer payMethod;

    @ApiModelProperty(value = "停车时长")
    private Integer parkLong;

    @ApiModelProperty(value = "车牌号")
    private String plateNo;

    @ApiModelProperty(value = "adapterId")
    private long adapterId = 19;

    @ApiModelProperty(value = "支付时间")
    private String payTime;

    @ApiModelProperty(value = "支付时间")
    private String orderNo;
}
