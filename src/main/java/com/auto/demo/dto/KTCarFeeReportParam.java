package com.auto.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/6/2 15:51
 */
@Data
@ApiModel(description = "科拓缴费记录数据")
public class KTCarFeeReportParam {

    @ApiModelProperty(value = "实收总金额")
    private Integer paidMoney;

    @ApiModelProperty(value = "付款方式")
    private Integer payMethod;

    @ApiModelProperty(value = "入场时间")
    private String entryTime;

    @ApiModelProperty(value = "车牌号")
    private String plateNo;

    @ApiModelProperty(value = "adapterId")
    private long adapterId = 36;

}
