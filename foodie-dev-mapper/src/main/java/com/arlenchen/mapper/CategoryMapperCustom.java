package com.arlenchen.mapper;

import com.arlenchen.pojo.Category;
import com.arlenchen.pojo.vo.CategoryVO;
import com.arlenchen.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author arlenchen
 */
public interface CategoryMapperCustom {

    /**
     *
     * 根据一级分类id查询子分类信息
     * @param rooCatId  一级分类id
     * @return List
     */
    List<CategoryVO> getSubCatList(Integer rooCatId);

    /**
     * 根据一级分类id查询出六条最新的产品信息
     *
     * @param map 一级分类id
     * @return list
     */
    List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String ,Object> map);
}