package com.arlenchen.pojo.vo;

import com.arlenchen.pojo.Items;
import com.arlenchen.pojo.ItemsImg;
import com.arlenchen.pojo.ItemsParam;
import com.arlenchen.pojo.ItemsSpec;

import java.util.List;

/**
 * 商品详情VO
 * @author arlenchen
 */
public class ItemInfoVO {
    Items items;
    List<ItemsImg> itemImgList;
    List<ItemsSpec> itemSpecList;
    ItemsParam itemParams;

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public List<ItemsImg> getItemImgList() {
        return itemImgList;
    }

    public void setItemImgList(List<ItemsImg> itemImgList) {
        this.itemImgList = itemImgList;
    }

    public List<ItemsSpec> getItemSpecList() {
        return itemSpecList;
    }

    public void setItemSpecList(List<ItemsSpec> itemSpecList) {
        this.itemSpecList = itemSpecList;
    }

    public ItemsParam getItemParams() {
        return itemParams;
    }

    public void setItemParams(ItemsParam itemParams) {
        this.itemParams = itemParams;
    }

    @Override
    public String toString() {
        return "ItemInfoVO{" +
                "items=" + items +
                ", itemImgList=" + itemImgList +
                ", itemSpecList=" + itemSpecList +
                ", itemParams=" + itemParams +
                '}';
    }
}
