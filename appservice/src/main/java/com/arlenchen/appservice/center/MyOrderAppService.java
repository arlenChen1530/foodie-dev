package com.arlenchen.appservice.center;

import com.arlenchen.utils.JsonResult;
import com.arlenchen.utils.PageGridResult;

/**
 * @author arlenchen
 */
public interface MyOrderAppService {

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
    JsonResult queryMyOrder(String orderId, String userId);
}
