package com.arlenchen.appservice.impl;

import com.arlenchen.appservice.ItemImgAppService;
import com.arlenchen.enums.YesOrNo;
import com.arlenchen.mapper.ItemsImgMapper;
import com.arlenchen.pojo.ItemsImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品图片APP服务
 *
 * @author arlenChen
 * @cretedTime 2020.12.28 13:56:00
 */
@Service
public class ItemImgAppServiceImpl implements ItemImgAppService {
    private final ItemsImgMapper itemsImgMapper;

    @Autowired
    public ItemImgAppServiceImpl(ItemsImgMapper itemsImgMapper) {
        this.itemsImgMapper = itemsImgMapper;
    }

    /**
     * 根据商品id查询商品主图
     *
     * @param itemId 商品id
     * @return url
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public String queryItemMainImdByItemId(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.type);
        ItemsImg result = itemsImgMapper.selectOne(itemsImg);
        return result != null ? result.getUrl() : "";
    }
}
