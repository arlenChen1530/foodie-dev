package com.arlenchen.mapper;

import com.arlenchen.pojo.Category;
import com.arlenchen.pojo.vo.CategoryVO;
import com.arlenchen.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {
    List<CategoryVO> getSubCatLIst(Integer rooCatId);

    List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String ,Object> map);
}