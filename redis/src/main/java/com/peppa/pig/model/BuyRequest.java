package com.peppa.pig.model;

import lombok.Data;

@Data
/**
 * 订单请求信息
 */
public class BuyRequest {
    /**
     * 商品Id
     */
    private Integer goodId;
    /**
     * 用户Id
     */
    private Integer userId;
    /**
     * 订单Id
     */
    private Integer orderId;
    /**
     * 订单信息
     */
    private BuyOrders buyOrders;
    /**
     * 0：未处理；1：正常；2：异常
     */
    private Integer responseStatus;
}
