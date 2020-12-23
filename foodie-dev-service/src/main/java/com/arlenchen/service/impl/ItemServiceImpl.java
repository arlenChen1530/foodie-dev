package com.arlenchen.service.impl;

import com.arlenchen.mapper.ItemsImgMapper;
import com.arlenchen.mapper.ItemsMapper;
import com.arlenchen.mapper.ItemsParamMapper;
import com.arlenchen.mapper.ItemsSpecMapper;
import com.arlenchen.pojo.Items;
import com.arlenchen.pojo.ItemsImg;
import com.arlenchen.pojo.ItemsParam;
import com.arlenchen.pojo.ItemsSpec;
import com.arlenchen.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ItemServiceImpl  implements ItemService {
    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;

    /**
     * 根据商品id查询产品详情
     * @param itemId 产品id
     * @return items
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }


    /**
     * 根据商品id查询产品图片
     *
     * @param itemId 商品id
     * @return 图片List
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsImgMapper.selectByExample(example);
    }

    /**
     * 根据商品id查询产品规格
     *
     * @param itemId 商品id
     * @return 图片List
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    /**
     * 根据商品id查询产品参数
     *
     * @param itemId 商品id
     * @return 产品参数
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return itemsParamMapper.selectOneByExample(example);
    }
}
