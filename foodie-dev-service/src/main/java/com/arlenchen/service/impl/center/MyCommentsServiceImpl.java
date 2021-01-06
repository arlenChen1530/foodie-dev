package com.arlenchen.service.impl.center;

import com.arlenchen.enums.YesOrNo;
import com.arlenchen.mapper.ItemsCommentsCustomMapper;
import com.arlenchen.mapper.OrderItemsMapper;
import com.arlenchen.mapper.OrderStatusMapper;
import com.arlenchen.mapper.OrdersMapper;
import com.arlenchen.pojo.OrderItems;
import com.arlenchen.pojo.OrderStatus;
import com.arlenchen.pojo.Orders;
import com.arlenchen.pojo.bo.center.OrderItemCommentBO;
import com.arlenchen.pojo.vo.MyCommentVO;
import com.arlenchen.service.center.MyCommentsService;
import com.arlenchen.utils.CommonUtils;
import com.arlenchen.utils.PageGridResult;
import com.github.pagehelper.PageHelper;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author arlenchen
 */
@Service
public class MyCommentsServiceImpl implements MyCommentsService {
    private final OrderItemsMapper orderItemsMapper;
    private final OrdersMapper ordersMapper;
    private final OrderStatusMapper orderStatusMapper;
    private final Sid sid;
    private final ItemsCommentsCustomMapper itemsCommentsCustomMapper;

    @Autowired
    public MyCommentsServiceImpl(OrderItemsMapper orderItemsMapper, Sid sid, ItemsCommentsCustomMapper itemsCommentsCustomMapper,
                                 OrdersMapper ordersMapper, OrderStatusMapper orderStatusMapper) {
        this.orderItemsMapper = orderItemsMapper;
        this.sid = sid;
        this.itemsCommentsCustomMapper = itemsCommentsCustomMapper;
        this.ordersMapper = ordersMapper;
        this.orderStatusMapper = orderStatusMapper;
    }

    /**
     * 查询订单下的所有商品
     *
     * @param orderId 订单
     * @return List
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public List<OrderItems> queryPendingCommentList(String orderId) {
        OrderItems orderItems = new OrderItems();
        orderItems.setOrderId(orderId);
        return orderItemsMapper.select(orderItems);
    }

    /**
     * 保存订单评价
     *
     * @param userId    用户
     * @param orderId   订单
     * @param itemsList 评价
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveComments(String userId, String orderId, List<OrderItemCommentBO> itemsList) {
        //1.保存评价 items_comments
        for (OrderItemCommentBO orderItemCommentBO : itemsList) {
            orderItemCommentBO.setUserId(userId);
            orderItemCommentBO.setId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        map.put("itemsList", itemsList);
        itemsCommentsCustomMapper.saveComments(map);
        //2.修改订单为已评价 orders
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setUserId(userId);
        orders.setIsComment(YesOrNo.YES.type);
        orders.setUpdatedTime(new Date());
        ordersMapper.updateByPrimaryKeySelective(orders);
        //3.修改订单状态的留言时间，orders_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    /**
     * 历史评价
     *
     * @param userId   用户
     * @param page     分页
     * @param pageSize 分页
     * @return PageGridResult
     */
    @Override
    public PageGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsCustomMapper.queryMyComments(map);
        return CommonUtils.setterPageGridResult(list, page);
    }
}
