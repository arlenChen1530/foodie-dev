package com.arlenchen.utils;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 工具类
 *
 * @author arlenchen
 */
public class CommonUtils {
    public static final String FOODIE_SHOP_CAT = "shopcat";
    public static final Integer PAGE_SIZE = 20;
    public static  PageGridResult setterPageGridResult(List<?> list, Integer page) {
        PageGridResult pageGridResult = new PageGridResult();
        PageInfo<?> pageList = new PageInfo<>(list);
        pageGridResult.setPage(page);
        pageGridResult.setRows(list);
        pageGridResult.setTotal(pageList.getPages());
        pageGridResult.setRecords(pageList.getTotal());
        return pageGridResult;
    }
}
