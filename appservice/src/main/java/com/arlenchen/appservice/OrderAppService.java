package com.arlenchen.appservice;

import com.arlenchen.pojo.bo.SubmitOrderBo;

public interface OrderAppService {
    /**
     * 用户下单
     *
     * @param submitOrderBo 下单数据
     */
    String createOrder(SubmitOrderBo submitOrderBo);

    /**
     * 修改订单状态
     *
     * @param orderId     订单Id
     * @param orderStatus 订单状态
     */
    void updateOrderStatus(String orderId, Integer orderStatus);
}
