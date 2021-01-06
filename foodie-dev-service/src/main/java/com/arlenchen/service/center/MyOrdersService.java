package com.arlenchen.service.center;

import com.arlenchen.pojo.Orders;
import com.arlenchen.utils.PageGridResult;

/**
 * 订单
 *
 * @author arlenchen
 */
public interface MyOrdersService {
    /**
     * 查询用户订单
     *
     * @param userId      用户
     * @param page        分页
     * @param pageSize    分页
     * @param orderStatus 订单状态
     * @return List
     */
    PageGridResult queryOrderList(String userId, Integer orderStatus, Integer page, Integer pageSize);

    /**
     * 订单发货
     *
     * @param orderId 订单
     */
    void ordersDeliver(String orderId);


    /**
     * 订单确认收货
     *
     * @param orderId 订单
     * @return boolean
     */
    boolean confirmReceive(String orderId);

    /**
     * 用户删除订单
     *
     * @param orderId 订单
     * @param userId  用户
     * @return boolean
     */
    boolean delete(String orderId, String userId);
    /**
     * 查询用户有该订单
     *
     * @param orderId 订单
     * @param userId  用户
     * @return Orders
     */
    Orders queryMyOrder(String orderId, String userId);

}
