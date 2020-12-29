package com.arlenchen.appservice;

import com.arlenchen.pojo.bo.SubmitOrderBo;
import com.arlenchen.pojo.vo.OrderVO;

public interface OrderAppService {
    /**
     * 用户下单
     *
     * @param submitOrderBo 下单数据
     */
    OrderVO createOrder(SubmitOrderBo submitOrderBo);

    /**
     * 修改订单状态
     *
     * @param orderId     订单Id
     * @param orderStatus 订单状态
     */
    void updateOrderStatus(String orderId, Integer orderStatus);
}
