package com.arlenchen.pojo.vo;

import com.arlenchen.pojo.bo.MerchantOrdersBO;
import com.arlenchen.pojo.bo.ShopCatBO;

import java.util.List;

/**
 * @author arlenchen
 */
public class OrderVO {

    private String orderId;
    private MerchantOrdersBO merchantOrdersBO;
    private List<ShopCatBO> toBeRemoveShopCartList;

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

    public List<ShopCatBO> getToBeRemoveShopCartList() {
        return toBeRemoveShopCartList;
    }

    public void setToBeRemoveShopCartList(List<ShopCatBO> toBeRemoveShopCartList) {
        this.toBeRemoveShopCartList = toBeRemoveShopCartList;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "orderId='" + orderId + '\'' +
                ", merchantOrdersBO=" + merchantOrdersBO +
                '}';
    }
}