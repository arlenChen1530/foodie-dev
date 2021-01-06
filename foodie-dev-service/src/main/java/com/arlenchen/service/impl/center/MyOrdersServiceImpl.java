package com.arlenchen.service.impl.center;

import com.arlenchen.enums.OrderStatusEnum;
import com.arlenchen.enums.YesOrNo;
import com.arlenchen.mapper.OrderStatusMapper;
import com.arlenchen.mapper.OrdersMapper;
import com.arlenchen.mapper.OrdersMapperCustom;
import com.arlenchen.pojo.OrderStatus;
import com.arlenchen.pojo.Orders;
import com.arlenchen.pojo.vo.MyOrdersVO;
import com.arlenchen.service.center.MyOrdersService;
import com.arlenchen.utils.CommonUtils;
import com.arlenchen.utils.PageGridResult;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单
 *
 * @author arlenchen
 */
@Service
public class MyOrdersServiceImpl implements MyOrdersService {
    private final OrdersMapperCustom ordersMapperCustom;
    private final OrdersMapper ordersMapper;
    private final OrderStatusMapper orderStatusMapper;

    @Autowired
    public MyOrdersServiceImpl(OrdersMapperCustom ordersMapperCustom, OrderStatusMapper orderStatusMapper, OrdersMapper ordersMapper) {
        this.ordersMapperCustom = ordersMapperCustom;
        this.orderStatusMapper = orderStatusMapper;
        this.ordersMapper = ordersMapper;
    }

    /**
     * 查询用户订单
     *
     * @param userId      用户
     * @param page        分页
     * @param pageSize    分页
     * @param orderStatus 订单状态
     * @return List
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public PageGridResult queryOrderList(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, pageSize);
        List<MyOrdersVO> list = ordersMapperCustom.queryOrderList(map);
        return CommonUtils.setterPageGridResult(list, page);
    }

    /**
     * 订单发货
     *
     * @param orderId 订单
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void ordersDeliver(String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        orderStatus.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        orderStatusMapper.updateByExampleSelective(orderStatus, example);
    }

    /**
     * 订单确认收货
     *
     * @param orderId 订单
     * @return boolean
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean confirmReceive(String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        orderStatus.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int result = orderStatusMapper.updateByExampleSelective(orderStatus, example);
        return result == 1;
    }

    /**
     * 用户删除订单
     *
     * @param orderId 订单
     * @param userId  用户
     * @return boolean
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean delete(String orderId, String userId) {
        Orders orders = new Orders();
        orders.setUpdatedTime(new Date());
        orders.setIsDelete(YesOrNo.YES.type);
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderId);
        criteria.andEqualTo("userId", userId);
        int result = ordersMapper.updateByExampleSelective(orders,example);
        return result == 1;
    }

    /**
     * 查询用户有该订单
     *
     * @param orderId 订单
     * @param userId  用户
     * @return Orders
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public Orders queryMyOrder(String orderId, String userId) {
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setUserId(userId);
        orders.setIsDelete(YesOrNo.NO.type);
        return ordersMapper.selectOne(orders);
    }
}
