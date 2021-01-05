package com.arlenchen.controller;

import com.arlenchen.pojo.bo.ShopCatBO;
import com.arlenchen.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author arlenchen
 */
@Api(value = "购物车接口", tags = "购物车相关接口")
@RestController
@RequestMapping("shopcat")
public class ShopCatController {
    @ApiOperation(value = "添加购物车", notes = "添加购物车", httpMethod = "POST")
    @PostMapping("/add")
    public JsonResult add(@ApiParam(name = "useId",value = "用户id",required = true) @RequestParam String useId,
                          @ApiParam(name = "shopCatBO",value = "商品",required = true) @RequestBody ShopCatBO shopCatBO,
                          HttpServletRequest request, HttpServletResponse response){
        if(StringUtils.isBlank(useId)){
             return  JsonResult.errorMsg("");
        }
        //todo:前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车数据到redis缓存中
        return  JsonResult.ok();
    }
    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "GET")
    @GetMapping("/del")
    public JsonResult del(@ApiParam(name = "useId",value = "用户id",required = true) @RequestParam String useId,
                          @ApiParam(name = "itemSpecId",value = "规格id",required = true) @RequestParam String itemSpecId,
                          HttpServletRequest request, HttpServletResponse response){
        if(StringUtils.isBlank(useId)||StringUtils.isBlank(useId)){
             return  JsonResult.errorMsg("");
        }
        //todo:前端用户在登录的情况下，删除购物车商品，会同时在后端同步删除redis中的缓存
        return  JsonResult.ok();
    }
}
