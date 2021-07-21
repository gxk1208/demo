package com.auto.demo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 出场数据提交参数
 *
 * @author liguanghui
 * @create 2021-4-9 15:47:46
 */
@Data
public class KetoPostPayReport {
    /**
     * 车牌号
     */
    private String plateNo;
    /**
     * 入场取票号/无牌车入场的卡号
     */
    private String cardNo;
    /**
     * 入场时间
     */
    private String entryTime;
    /**
     * 支付时间 yyyy-MM-dd HH:mm:ss
     */
    private String payTime;

    /**
     * 支付来源 详见参数枚举 1.6.5
     */
    private Integer paySource;

    /**
     * 收费终端，详见参数枚举1.6.4
     */
    private Integer payType;

    /**
     * 付款方式，详见参数枚举 1.6.3
     */
    private Integer payMethod;

    /**
     * 应收总金额（单位分）
     */
    private Integer totalMoney;
    /**
     * 实收总金额（单位分）
     */
    private Integer paidMoney;
    /**
     * 抵扣时长（单位秒）
     */
    private Integer freeTime;
    /**
     * 抵扣金额（单位分）
     */
    private Integer freeMoney;
    /**
     * 车场订单号
     */
    private String orderNo;
    /**
     * 支付定单号
     */
    private String paymentOrderNo;
    /**
     * 车牌颜色 详见参数枚举1.6.11（白、黑、灰等）
     */
    private String plateColor;


    private Integer parkLong;
}
