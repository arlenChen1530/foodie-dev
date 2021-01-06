package com.arlenchen.pojo.vo;


/**
 * 订单商品
 *
 * @author arlenchen
 */
public class MyOrdersItemsVO {
    /**
     * 订单商品id
     */
    private String itemId;
    /**
     * 订单商品名称
     */
    private String itemName;
    /**
     * 订单商品快照
     */
    private String itemImg;
    /**
     * 订单商品规格id
     */
    private String itemSpecId;
    /**
     * 订单商品规格名称
     */
    private String itemSpecName;
    /**
     * 订单商品购买数量
     */
    private Integer buyCounts;
    /**
     * 订单商品单价
     */
    private Integer price;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getItemSpecId() {
        return itemSpecId;
    }

    public void setItemSpecId(String itemSpecId) {
        this.itemSpecId = itemSpecId;
    }

    public String getItemSpecName() {
        return itemSpecName;
    }

    public void setItemSpecName(String itemSpecName) {
        this.itemSpecName = itemSpecName;
    }

    public Integer getBuyCounts() {
        return buyCounts;
    }

    public void setBuyCounts(Integer buyCounts) {
        this.buyCounts = buyCounts;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrdersItemsVO{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemImg='" + itemImg + '\'' +
                ", itemSpecId='" + itemSpecId + '\'' +
                ", itemSpecName='" + itemSpecName + '\'' +
                ", buyCounts=" + buyCounts +
                ", price=" + price +
                '}';
    }
}
