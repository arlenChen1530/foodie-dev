package com.arlenchen.service;


import com.arlenchen.pojo.Items;
import com.arlenchen.pojo.ItemsImg;
import com.arlenchen.pojo.ItemsParam;
import com.arlenchen.pojo.ItemsSpec;

import java.util.List;

/**
 * 商品
 */
public interface ItemService {
    /**
     * 根据商品id查询产品详情
     *
     * @param itemId 产品id
     * @return items
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品id查询产品图片
     *
     * @param itemId 商品id
     * @return 图片List
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询产品规格
     *
     * @param itemId 商品id
     * @return 图片List
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询产品参数
     *
     * @param itemId 商品id
     * @return 产品参数
     */
    ItemsParam queryItemParam(String itemId);
}
