package com.arlenchen.controller;

import com.arlenchen.appservice.CarouselAppService;
import com.arlenchen.appservice.CategoryAppService;
import com.arlenchen.pojo.Carousel;
import com.arlenchen.pojo.Category;
import com.arlenchen.pojo.vo.CategoryVO;
import com.arlenchen.pojo.vo.NewItemsVO;
import com.arlenchen.utils.JsonResult;
import com.arlenchen.utils.JsonUtils;
import com.arlenchen.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author arlenchen
 */
@Api(value = "首页信息",tags = "首页信息相关接口")
@RestController
@RequestMapping("index")
public class IndexController {
    private final CarouselAppService carouselAppService;
    private final CategoryAppService categoryAppService;
    private final RedisOperator redisOperator;
@Autowired
    public IndexController(CarouselAppService carouselAppService, CategoryAppService categoryAppService,RedisOperator redisOperator) {
        this.carouselAppService = carouselAppService;
        this.categoryAppService = categoryAppService;
        this.redisOperator = redisOperator;
    }

    @ApiOperation(value = "获取首页轮播图列表",notes = "获取首页轮播图列表",httpMethod = "GET")
    @GetMapping("/carousel")
    public JsonResult carousel() {
        /*
         * 缓存重置时机
         * 1.后台运营系统，一旦广告发生更改，就可以删除缓存，然后重置
         * 2.定时重置
         * 3.每个轮播图可能都是一个广告，每个广告都会有一个过期时间，过期了，再重置
         */
       String carouselStr= redisOperator.get("carousel");
       if(StringUtils.isBlank(carouselStr)){
           List<Carousel> list=carouselAppService.queryAll(1);
           redisOperator.set("carousel", JsonUtils.objectToJson(list));
           return  JsonResult.ok(list);
       }
        List<Carousel> list=JsonUtils.jsonToList(carouselStr,Carousel.class);
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
        List<CategoryVO> list=categoryAppService.getSubCatList(rootCatId);
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
