package com.arlenchen.service.center;

import com.arlenchen.pojo.OrderItems;
import com.arlenchen.pojo.bo.center.OrderItemCommentBO;
import com.arlenchen.utils.PageGridResult;

import java.util.List;

/**
 * @author arlenchen
 */
public interface MyCommentsService {

    /**
     * 查询订单下的所有商品
     *
     * @param orderId 订单
     * @return List
     */
    List<OrderItems> queryPendingCommentList(String orderId);

    /**
     * 保存订单评价
     *
     * @param userId    用户
     * @param orderId   订单
     * @param itemsList 评价
     */
    void saveComments(String userId, String orderId, List<OrderItemCommentBO> itemsList);

    /**
     * 历史评价
     *
     * @param userId   用户
     * @param page     分页
     * @param pageSize 分页
     * @return PageGridResult
     */
    PageGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
