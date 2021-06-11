package com.auto.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author gxk
 * @version 1.0
 * @date 2021/6/2 15:51
 */
@Data
@ApiModel(description = "科拓缴费记录数据")
public class KTCarFeeReportParam {

    @ApiModelProperty(value = "记录列表")
    List<KTCarFeeReportDto> reportDtos;

    private long adapterId = 36;
}
