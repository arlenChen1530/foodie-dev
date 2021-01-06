package com.arlenchen.pojo.vo;

import java.util.Date;
import java.util.List;

/**
 * 订单
 *
 * @author arlenchen
 */
public class MyOrdersVO {
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 订单创建时间
     */
    private Date createdTime;
    /**
     * 订单支付方式
     */
    private Integer payMethod;
    /**
     * 订单付款金额
     */
    private Integer realPayAmount;
    /**
     * 订单邮费
     */
    private Integer postAmount;
    /**
     * 订单状态
     */
    private Integer orderStatus;
    /**
     * 订单明细列表
     */
    private List<MyOrdersItemsVO> itemsList;
    /**
     * 买家是否评价;1：已评价，0：未评价
     */
    private Integer isComment;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public Integer getRealPayAmount() {
        return realPayAmount;
    }

    public void setRealPayAmount(Integer realPayAmount) {
        this.realPayAmount = realPayAmount;
    }

    public Integer getPostAmount() {
        return postAmount;
    }

    public void setPostAmount(Integer postAmount) {
        this.postAmount = postAmount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<MyOrdersItemsVO> getItemsList() {
        return itemsList;
    }

    public void setItemsList(List<MyOrdersItemsVO> itemsList) {
        this.itemsList = itemsList;
    }

    public Integer getIsComment() {
        return isComment;
    }

    public void setIsComment(Integer isComment) {
        this.isComment = isComment;
    }

    @Override
    public String toString() {
        return "OrdersVO{" +
                "orderId='" + orderId + '\'' +
                ", createdTime=" + createdTime +
                ", payMethod=" + payMethod +
                ", realPayAmount=" + realPayAmount +
                ", postAmount=" + postAmount +
                ", orderStatus=" + orderStatus +
                ", itemsList=" + itemsList +
                '}';
    }
}
