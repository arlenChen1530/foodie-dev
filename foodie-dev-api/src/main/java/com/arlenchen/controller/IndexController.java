package com.arlenchen.controller;

import com.arlenchen.pojo.Carousel;
import com.arlenchen.pojo.Category;
import com.arlenchen.pojo.bo.UserBO;
import com.arlenchen.pojo.vo.CategoryVO;
import com.arlenchen.pojo.vo.NewItemsVO;
import com.arlenchen.service.CarouselService;
import com.arlenchen.service.CategoryService;
import com.arlenchen.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "首页信息",tags = "首页信息相关接口")
@RestController
@RequestMapping("index")
public class IndexController {
    final static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public JsonResult carousel() {
        List<Carousel> list=carouselService.queryAll(1);
        return  JsonResult.ok(list);
    }
    @ApiOperation(value = "获取商品分类(一级分类)",notes = "获取商品分类(一级分类)",httpMethod = "GET")
    @GetMapping("/cats")
    public JsonResult cats() {
        List<Category> list=categoryService.queryAllRootLevelCat();
        return  JsonResult.ok(list);
    }
    @ApiOperation(value = "获取商品子分类",notes = "获取商品子分类",httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JsonResult subCat(
            @ApiParam(name ="rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId) {
        if(rootCatId==null){
            return  JsonResult.errorMsg("请选择一级分类");
        }
        List<CategoryVO> list=categoryService.getSubCatLIst(rootCatId);
        return  JsonResult.ok(list);
    }
    @ApiOperation(value = "查询一级分类下的最新6条商品数据",notes = "查询一级分类下的最新6条商品数据",httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JsonResult sixNewItems(
            @ApiParam(name ="rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId) {
        if(rootCatId==null){
            return  JsonResult.errorMsg("请选择一级分类");
        }
        List<NewItemsVO> list=categoryService.getSixNewItemsLazy(rootCatId);
        return  JsonResult.ok(list);
    }
}
