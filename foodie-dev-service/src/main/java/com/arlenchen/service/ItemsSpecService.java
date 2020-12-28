package com.arlenchen.service;


import com.arlenchen.pojo.ItemsSpec;

import java.util.List;

/**
 * 商品规格
 */
public interface ItemsSpecService {
    /**
     * 根据id查询商品规格
     *
     * @param id id
     * @return ItemsSpec
     */
    ItemsSpec queryById(String id);

    /**
     * 根据ids查询商品规格
     *
     * @param ids id
     * @return List
     */
    List<ItemsSpec> ListByIds(String ids);

    /**
     * 减少库存
     *
     * @param specId    规格id
     * @param buyCounts 购买数量
     */
    void decreaseItemSpecStock(String specId, int buyCounts);
}
