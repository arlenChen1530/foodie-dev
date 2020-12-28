package com.arlenchen.appservice;

public interface ItemImgAppService {
    /**
     * 根据商品id查询商品主图
     *
     * @param itemId 商品id
     * @return url
     */
    String queryItemMainImdByItemId(String itemId);
}
