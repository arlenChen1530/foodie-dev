package com.arlenchen.pojo.vo;

/**
 * 商品搜索VO
 * @author arlenchen
 */
public class SearchItemsVO {
 private  String itemId;
 private  String itemName;
 private  Integer sellCounts;
 private  String imgUrl;
 private  Integer price;

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

    public Integer getSellCounts() {
        return sellCounts;
    }

    public void setSellCounts(Integer sellCounts) {
        this.sellCounts = sellCounts;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "SearchItemsVO{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", sellCounts=" + sellCounts +
                ", imgUrl='" + imgUrl + '\'' +
                ", price=" + price +
                '}';
    }
}
