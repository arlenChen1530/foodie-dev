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
import com.arlenchen.pojo.bo.SubmitOrderBo;
import com.arlenchen.service.ItemsSpecService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderAppServiceImpl implements OrderAppService {
    @Autowired
    private Sid sid;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private AddressAppService addressAppService;
    @Autowired
    private ItemsSpecService itemsSpecService;
    @Autowired
    private ItemImgAppService itemImgAppService;
    @Autowired
    private ItemAppService itemAppService;

    @Override
    public void createOrder(SubmitOrderBo submitOrderBo) {
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
        List<ItemsSpec> specList = itemsSpecService.ListByIds(itemSpecIds);
        createOrderItemsList(specList, orders);
        //3.创建订单状态信息
        OrderStatus waitPayOrderStatus =new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setCreatedTime(new Date());
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        ordersMapper.insert(orders);
        orderStatusMapper.insert(waitPayOrderStatus);
    }

    /**
     * 创建订单子订单信息
     *
     * @param specList 商品规格信息
     * @param orders   订单
     * @return List
     */
    private void createOrderItemsList(List<ItemsSpec> specList, Orders orders) {
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
            itemsSpecService.decreaseItemSpecStock(itemsSpec.getId(),buyCount);
        }
        orders.setTotalAmount(totalAmount);
        orders.setPostAmount(realPayAmount);
    }
}
