package com.arlenchen.appservice.impl;

import com.arlenchen.appservice.ItemAppService;
import com.arlenchen.enums.CommentLevel;
import com.arlenchen.mapper.*;
import com.arlenchen.pojo.*;
import com.arlenchen.pojo.vo.CommentLevelCountsVO;
import com.arlenchen.pojo.vo.ItemCommentVO;
import com.arlenchen.pojo.vo.SearchItemsVO;
import com.arlenchen.pojo.vo.ShopCatVO;
import com.arlenchen.utils.CommonUtils;
import com.arlenchen.utils.DesensitizationUtil;
import com.arlenchen.utils.PageGridResult;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author arlenchen
 */
@Service
public class ItemAppServiceImpl implements ItemAppService {
    private final ItemsMapper itemsMapper;
    private final ItemsImgMapper itemsImgMapper;
    private final ItemsSpecMapper itemsSpecMapper;
    private final ItemsParamMapper itemsParamMapper;
    private final ItemsCommentsMapper itemsCommentsMapper;
    private final ItemsMapperCustom itemsMapperCustom;

    @Autowired
    public ItemAppServiceImpl(ItemsMapper itemsMapper, ItemsImgMapper itemsImgMapper, ItemsSpecMapper itemsSpecMapper, ItemsParamMapper itemsParamMapper,
                              ItemsCommentsMapper itemsCommentsMapper, ItemsMapperCustom itemsMapperCustom) {
        this.itemsMapper = itemsMapper;
        this.itemsImgMapper = itemsImgMapper;
        this.itemsSpecMapper = itemsSpecMapper;
        this.itemsParamMapper = itemsParamMapper;
        this.itemsCommentsMapper = itemsCommentsMapper;
        this.itemsMapperCustom = itemsMapperCustom;
    }

    /**
     * 根据商品id查询产品详情
     *
     * @param itemId 产品id
     * @return items
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
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
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(example);
    }

    /**
     * 根据商品id查询产品规格
     *
     * @param itemId 商品id
     * @return 图片List
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    /**
     * 根据商品id查询产品参数
     *
     * @param itemId 商品id
     * @return 产品参数
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    /**
     * 获取评价数量
     *
     * @param itemId 商品id
     * @return 评价数量
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        CommentLevelCountsVO commentLevelCountsVO = new CommentLevelCountsVO();
        commentLevelCountsVO.setGoodCounts(getCommentCounts(itemId, CommentLevel.good.value));
        commentLevelCountsVO.setNormalCounts(getCommentCounts(itemId, CommentLevel.normal.value));
        commentLevelCountsVO.setBadCounts(getCommentCounts(itemId, CommentLevel.bad.value));
        commentLevelCountsVO.setTotalCounts(commentLevelCountsVO.getGoodCounts() + commentLevelCountsVO.getNormalCounts() +
                commentLevelCountsVO.getBadCounts());
        return commentLevelCountsVO;
    }

    /**
     *
     * 根据商品id和级别查询商品评价
     *
     * @param itemId 商品id
     * @param level  级别
     * @param page 第几页
     * @param pageSize 每页数量
     * @return List
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public PageGridResult queryPageComments(String itemId, Integer level,Integer page,Integer pageSize) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("itemId", itemId);
        map.put("level", level);
        PageHelper.startPage(page,pageSize);
        List<ItemCommentVO> list=itemsMapperCustom.queryItemComments(map);
        for (ItemCommentVO itemCommentVO : list) {
            itemCommentVO.setNickName(DesensitizationUtil.commonDisplay(itemCommentVO.getNickName()));
        }
        return  CommonUtils.setterPageGridResult(list,page);
    }

    /**
     * 根据搜索条件查询商品
     *
     * @param keywords 关键字
     * @param sort     排序方式
     * @param page     页数
     * @param pageSize 每页数量
     * @return List
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public PageGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("keywords", keywords);
        map.put("sort", sort);
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list=itemsMapperCustom.searchItems(map);
        return  CommonUtils.setterPageGridResult(list,page);
    }

    /**
     * 根据三级分类查询商品
     *
     * @param catId 三级分类Id
     * @param sort     排序方式
     * @param page     页数
     * @param pageSize 每页数量
     * @return List
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public PageGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("catId", catId);
        map.put("sort", sort);
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list=itemsMapperCustom.searchItemsByThirdCat(map);
        return  CommonUtils.setterPageGridResult(list,page);
    }
    /**
     * 根据规格id查询购物车中最新的商品数据（用于刷新渲染购物车中的商品数据）
     *
     * @param specIds 规格id
     * @return List<ShopCatVO> 购物车数据
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public List<ShopCatVO> queryItemsBySpecIds(String specIds) {
        String[] ids=specIds.split(",");
        List<String> idList=new ArrayList<>();
        Collections.addAll(idList,ids);
        return itemsMapperCustom.queryItemsBySpecIds(idList);
    }

    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    Integer getCommentCounts(String itemId, Integer level) {
        ItemsComments itemsComments = new ItemsComments();
        itemsComments.setItemId(itemId);
        if (level != null) {
            itemsComments.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(itemsComments);
    }
}
