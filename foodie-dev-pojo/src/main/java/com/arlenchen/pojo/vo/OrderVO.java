package com.arlenchen.pojo.vo;

import com.arlenchen.pojo.bo.MerchantOrdersBO;

/**
 * @author arlenchen
 */
public class OrderVO {

    private String orderId;
    private MerchantOrdersBO merchantOrdersBO;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersBO getMerchantOrdersBO() {
        return merchantOrdersBO;
    }

    public void setMerchantOrdersBO(MerchantOrdersBO merchantOrdersBO) {
        this.merchantOrdersBO = merchantOrdersBO;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "orderId='" + orderId + '\'' +
                ", merchantOrdersBO=" + merchantOrdersBO +
                '}';
    }
}