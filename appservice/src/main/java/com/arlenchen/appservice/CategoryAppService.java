package com.arlenchen.appservice;

import com.arlenchen.pojo.Category;
import com.arlenchen.pojo.vo.CategoryVO;
import com.arlenchen.pojo.vo.NewItemsVO;

import java.util.List;

public interface CategoryAppService {
    /**
     * 查询所有一级分类
     *
     * @return List
     */
    List<Category> queryAllRootLevelCat();

    /**
     *
     * 根据一级分类id查询子分类信息
     * @param rootCatId  一级分类id
     * @return List
     */
    List<CategoryVO> getSubCatLIst(Integer rootCatId);

    /**
     * 根据一级分类id查询出六条最新的产品信息
     *
     * @param rootCatId 一级分类id
     * @return list
     */
 List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
