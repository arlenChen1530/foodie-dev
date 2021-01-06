package com.arlenchen.appservice.impl;

import com.arlenchen.appservice.AddressAppService;
import com.arlenchen.appservice.ItemAppService;
import com.arlenchen.appservice.ItemImgAppService;
import com.arlenchen.appservice.OrderAppService;
import com.arlenchen.enums.OrderStatusEnum;
import com.arlenchen.enums.YesOrNo;
import com.arlenchen.mapper.OrderItemsMapper;
import com.arlenchen.mapper.OrderStatusMapper;
import com.arlenchen.mapper.OrdersMapper;
import com.arlenchen.pojo.*;
import com.arlenchen.pojo.bo.MerchantOrdersBO;
import com.arlenchen.pojo.bo.SubmitOrderBO;
import com.arlenchen.pojo.vo.OrderStatusVO;
import com.arlenchen.pojo.vo.OrderVO;
import com.arlenchen.service.ItemsSpecService;
import com.arlenchen.utils.DateUtil;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author arlenchen
 */
@Service
public class OrderAppServiceImpl implements OrderAppService {
    private final Sid sid;
    private final OrdersMapper ordersMapper;
    private final OrderItemsMapper orderItemsMapper;
    private final OrderStatusMapper orderStatusMapper;
    private final AddressAppService addressAppService;
    private final ItemsSpecService itemsSpecService;
    private final ItemImgAppService itemImgAppService;
    private final ItemAppService itemAppService;

    @Autowired
    public OrderAppServiceImpl(Sid sid, OrdersMapper ordersMapper, OrderItemsMapper orderItemsMapper, OrderStatusMapper orderStatusMapper, AddressAppService addressAppService, ItemsSpecService itemsSpecService, ItemImgAppService itemImgAppService, ItemAppService itemAppService) {
        this.sid = sid;
        this.ordersMapper = ordersMapper;
        this.orderItemsMapper = orderItemsMapper;
        this.orderStatusMapper = orderStatusMapper;
        this.addressAppService = addressAppService;
        this.itemsSpecService = itemsSpecService;
        this.itemImgAppService = itemImgAppService;
        this.itemAppService = itemAppService;
    }

    /**
     * 用户下单
     *
     * @param submitOrderBo 下单数据
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBo) {
        String userId = submitOrderBo.getUserId();
        String itemSpecIds = submitOrderBo.getItemSpecIds();
        String addressId = submitOrderBo.getAddressId();
        Integer payMethod = submitOrderBo.getPayMethod();
        String leftMsg = submitOrderBo.getLeftMsg();
        UserAddress userAddress = addressAppService.queryUserAddress(userId, addressId);
        String orderId = sid.nextShort();
        //设置邮费
        Integer postAmount = 0;
        //1.创建订单信息
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setUserId(userId);
        if (userAddress != null) {
            orders.setReceiverName(userAddress.getReceiver());
            orders.setReceiverMobile(userAddress.getMobile());
            orders.setReceiverAddress(userAddress.getProvince() + " " + userAddress.getCity() + " " + userAddress.getDistrict() + " " + userAddress.getDetail());
        }
        orders.setPostAmount(postAmount);
        orders.setPayMethod(payMethod);
        orders.setLeftMsg(leftMsg);
        orders.setIsComment(YesOrNo.NO.type);
        orders.setIsDelete(YesOrNo.NO.type);
        orders.setCreatedTime(new Date());
        orders.setUpdatedTime(new Date());
        //2.创建订单子订单信息
        List<ItemsSpec> specList = itemsSpecService.listByIds(itemSpecIds);
        createOrderItemsList(specList, orders);
        //3.创建订单状态信息
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setCreatedTime(new Date());
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        ordersMapper.insert(orders);
        orderStatusMapper.insert(waitPayOrderStatus);
        //4.创建商户订单，用于传给支付中心
        MerchantOrdersBO merchantOrdersBO = new MerchantOrdersBO();
        merchantOrdersBO.setAmount(orders.getRealPayAmount() + postAmount);
        merchantOrdersBO.setMerchantOrderId(orderId);
        merchantOrdersBO.setMerchantUserId(userId);
        merchantOrdersBO.setPayMethod(payMethod);
        merchantOrdersBO.setReturnUrl("");
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersBO(merchantOrdersBO);
        return orderVO;
    }

    /**
     * 修改订单状态
     *
     * @param orderId     订单Id
     * @param orderStatus 订单状态
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    /**
     * 查询订单是否支付成功
     *
     * @param orderId 订单Id
     * @return OrderStatus
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public OrderStatusVO getPaidOrderInfo(String orderId) {
        OrderStatus orderStatus = orderStatusMapper.selectByPrimaryKey(orderId);
        if (orderStatus != null) {
            OrderStatusVO orderStatusVO = new OrderStatusVO();
            BeanUtils.copyProperties(orderStatus, orderStatusVO);
            orderStatusVO.setOrderId(orderId);
            return orderStatusVO;
        }
        return null;
    }

    /**
     * 关闭订单
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void closeOrder() {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> statusList = orderStatusMapper.select(orderStatus);
        for (OrderStatus closeOrder : statusList) {
            Date createdTime = closeOrder.getCreatedTime();
            int dateDiff = DateUtil.daysBetween(createdTime, new Date());
            if (dateDiff >= 1) {
                doCloseOrder(closeOrder.getOrderId());
            }
        }

    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    void doCloseOrder(String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.CLOSE.type);
        orderStatus.setCloseTime(new Date());
        orderStatus.setOrderId(orderId);
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    /**
     * 创建订单子订单信息
     *
     * @param specList 商品规格信息
     * @param orders   订单
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    void createOrderItemsList(List<ItemsSpec> specList, Orders orders) {
        //2.1根据规格id，查询规格获取价格
        int totalAmount = 0;
        int realPayAmount = 0;
        for (ItemsSpec itemsSpec : specList) {
            //todo:整个redis后，商品购买的数量重新从redis中获取
            int buyCount = 1;
            totalAmount += itemsSpec.getPriceNormal() != null ? itemsSpec.getPriceNormal() * buyCount : 0;
            realPayAmount += itemsSpec.getPriceDiscount() != null ? itemsSpec.getPriceDiscount() * buyCount : 0;
            //2.2根据商品id获取商品信息和主图
            String itemId = itemsSpec.getItemId();
            Items items = itemAppService.queryItemById(itemId);
            String url = itemImgAppService.queryItemMainImdByItemId(itemId);
            //2.3循环保存子订单到数据库
            OrderItems orderItems = new OrderItems();
            String subOrderId = sid.nextShort();
            orderItems.setId(subOrderId);
            orderItems.setOrderId(orders.getId());
            orderItems.setBuyCounts(buyCount);
            orderItems.setItemId(itemId);
            orderItems.setItemName(items.getItemName());
            orderItems.setItemImg(url);
            orderItems.setItemSpecId(itemsSpec.getId());
            orderItems.setItemSpecName(itemsSpec.getName());
            orderItems.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(orderItems);
            //2.4在用户提交订单以后，规格表中需要扣除库存
            itemsSpecService.decreaseItemSpecStock(itemsSpec.getId(), buyCount);
        }
        orders.setTotalAmount(totalAmount);
        orders.setRealPayAmount(realPayAmount);
    }
}
