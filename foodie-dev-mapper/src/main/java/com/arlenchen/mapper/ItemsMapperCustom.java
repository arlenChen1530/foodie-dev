package com.arlenchen.mapper;

import com.arlenchen.pojo.vo.ItemCommentVO;
import com.arlenchen.pojo.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {
    /**
     * 根据商品id和级别查询商品评价
     *
     * @param paramsMap 查询参数
     * @return List
     */
    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> paramsMap);

    /**
     * 根据搜索条件查询商品
     *
     * @param paramsMap 查询参数
     * @return List
     */
    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> paramsMap);

    /**
     * 根据三级分类查询商品
     *
     * @param paramsMap 查询参数
     * @return List
     */
    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> paramsMap);
}