package com.arlenchen.enums;

/**
 * 订单状态 枚举
 *
 * @author arlenchen
 */
public enum OrderStatusEnum {
    /**
     * 待付款
     */
    WAIT_PAY(10, "待付款"),
    /**
     * 已付款，待发货
     */
    WAIT_DELIVER(20, "已付款，待发货"),
    /**
     * 已发货，待收货
     */
    WAIT_RECEIVE(30, "已发货，待收货"),
    /**
     * 交易成功
     */
    SUCCESS(40, "交易成功"),
    /**
     * 交易关闭
     */
    CLOSE(50, "交易关闭");

    public final Integer type;
    public final String value;

    OrderStatusEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

}
