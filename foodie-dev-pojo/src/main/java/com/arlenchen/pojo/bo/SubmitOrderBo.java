package com.arlenchen.pojo.bo;

/**
 * 订单提交Bo
 */
public class SubmitOrderBo {
   private String userId;
   private String itemSpecIds;
   private String addressId;
    /**
     * 支付方式
     */
   private Integer payMethod;
    /**
     * 买家备注留言
     */
   private String leftMsg;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemSpecIds() {
        return itemSpecIds;
    }

    public void setItemSpecIds(String itemSpecIds) {
        this.itemSpecIds = itemSpecIds;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getLeftMsg() {
        return leftMsg;
    }

    public void setLeftMsg(String leftMsg) {
        this.leftMsg = leftMsg;
    }
}
