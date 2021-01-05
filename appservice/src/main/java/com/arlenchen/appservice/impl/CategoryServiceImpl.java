package com.arlenchen.appservice.impl;

import com.arlenchen.appservice.CategoryAppService;
import com.arlenchen.mapper.CategoryMapper;
import com.arlenchen.mapper.CategoryMapperCustom;
import com.arlenchen.pojo.Category;
import com.arlenchen.pojo.vo.CategoryVO;
import com.arlenchen.pojo.vo.NewItemsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author arlenchen
 */
@Service
public class CategoryServiceImpl implements CategoryAppService {

    private final CategoryMapper categoryMapper;
    private final CategoryMapperCustom categoryMapperCustom;
@Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryMapperCustom categoryMapperCustom) {
        this.categoryMapper = categoryMapper;
        this.categoryMapperCustom = categoryMapperCustom;
    }

    /**
     * 查询所有一级分类
     *
     * @return List
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example=new Example(Category.class);
        Example.Criteria criteria =example.createCriteria();
        criteria.andEqualTo("type",1);
        return categoryMapper.selectByExample(example);
    }

    /**
     *
     * 根据一级分类id查询子分类信息
     * @param rootCatId  一级分类id
     * @return List
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        return categoryMapperCustom.getSubCatList(rootCatId);
    }

    /**
     * 根据一级分类id查询出六条最新的产品信息
     *
     * @param rootCatId 一级分类id
     * @return list
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {
        Map<String,Object> map =new HashMap<>(16);
        map.put("rootCatId",rootCatId);
        return categoryMapperCustom.getSixNewItemsLazy(map);
    }
}
