package com.arlenchen.appservice;

import com.arlenchen.pojo.bo.ShopCatBO;
import com.arlenchen.pojo.bo.SubmitOrderBO;
import com.arlenchen.pojo.vo.OrderStatusVO;
import com.arlenchen.pojo.vo.OrderVO;

import java.util.List;

/**
 * @author arlenchen
 */
public interface OrderAppService {
    /**
     * 用户下单
     *
     * @param list 购物车数据
     * @param submitOrderBo 下单数据
     * @return  下单结果
     */
    OrderVO createOrder(List<ShopCatBO> list,SubmitOrderBO submitOrderBo);

    /**
     * 修改订单状态
     *
     * @param orderId     订单Id
     * @param orderStatus 订单状态
     */
    void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单是否支付成功
     *
     * @param orderId 订单Id
     * @return OrderStatus
     */
    OrderStatusVO getPaidOrderInfo(String orderId);

    /**
     * 关闭订单
     */
    void closeOrder();
}
