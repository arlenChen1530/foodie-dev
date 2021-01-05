package com.arlenchen.appservice;


import com.arlenchen.pojo.Items;
import com.arlenchen.pojo.ItemsImg;
import com.arlenchen.pojo.ItemsParam;
import com.arlenchen.pojo.ItemsSpec;
import com.arlenchen.pojo.vo.CommentLevelCountsVO;
import com.arlenchen.pojo.vo.ShopCatVO;
import com.arlenchen.utils.PageGridResult;

import java.util.List;

/**
 * 商品
 * @author arlenchen
 */
public interface ItemAppService {
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

    /**
     * 获取评价数量
     *
     * @param itemId 商品id
     * @return 评价数量
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id和级别查询商品评价
     *
     * @param itemId   商品id
     * @param level    级别
     * @param page     第几页
     * @param pageSize 每页数量
     * @return List
     */
    PageGridResult queryPageComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 根据搜索条件查询商品
     *
     * @param keywords 关键字
     * @param sort     排序方式
     * @param page     页数
     * @param pageSize 每页数量
     * @return List
     */
    PageGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 根据三级分类查询商品
     *
     * @param catId    三级分类Id
     * @param sort     排序方式
     * @param page     页数
     * @param pageSize 每页数量
     * @return List
     */
    PageGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * 根据规格id查询购物车中最新的商品数据（用于刷新渲染购物车中的商品数据）
     *
     * @param specIds 规格id
     * @return List<ShopCatVO> 购物车数据
     */
    List<ShopCatVO> queryItemsBySpecIds(String specIds);
}
