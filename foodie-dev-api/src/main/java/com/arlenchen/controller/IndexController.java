package com.arlenchen.controller;

import com.arlenchen.appservice.CarouselAppService;
import com.arlenchen.appservice.CategoryAppService;
import com.arlenchen.pojo.Carousel;
import com.arlenchen.pojo.Category;
import com.arlenchen.pojo.vo.CategoryVO;
import com.arlenchen.pojo.vo.NewItemsVO;
import com.arlenchen.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "首页信息",tags = "首页信息相关接口")
@RestController
@RequestMapping("index")
public class IndexController {
    final static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private CarouselAppService carouselAppService;
    @Autowired
    private CategoryAppService categoryAppService;

    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public JsonResult carousel() {
        List<Carousel> list=carouselAppService.queryAll(1);
        return  JsonResult.ok(list);
    }
    @ApiOperation(value = "获取商品分类(一级分类)",notes = "获取商品分类(一级分类)",httpMethod = "GET")
    @GetMapping("/cats")
    public JsonResult cats() {
        List<Category> list=categoryAppService.queryAllRootLevelCat();
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
        List<CategoryVO> list=categoryAppService.getSubCatLIst(rootCatId);
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
        List<NewItemsVO> list=categoryAppService.getSixNewItemsLazy(rootCatId);
        return  JsonResult.ok(list);
    }
}
