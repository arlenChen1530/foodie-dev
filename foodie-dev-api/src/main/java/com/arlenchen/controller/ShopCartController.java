package com.arlenchen.controller;

import com.arlenchen.pojo.Carousel;
import com.arlenchen.pojo.bo.ShopCatBO;
import com.arlenchen.utils.JsonResult;
import com.arlenchen.utils.JsonUtils;
import com.arlenchen.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author arlenchen
 */
@Api(value = "购物车接口", tags = "购物车相关接口")
@RestController
@RequestMapping("shopcart")
public class ShopCartController extends BaseController {
    private final RedisOperator redisOperator;

    @Autowired
    public ShopCartController(RedisOperator redisOperator) {
        this.redisOperator = redisOperator;
    }

    @ApiOperation(value = "添加购物车", notes = "添加购物车", httpMethod = "POST")
    @PostMapping("/add")
    public JsonResult add(@ApiParam(name = "useId", value = "用户id", required = true) @RequestParam String useId,
                          @ApiParam(name = "shopCatrBO", value = "商品", required = true) @RequestBody ShopCatBO shopCatBO,
                          HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(useId)) {
            return JsonResult.errorMsg("");
        }
        //前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车数据到redis缓存中
        //需要判断当前购物车中包含已经存在的商品，如果存在则累加购买数量
        String shopCatJson = redisOperator.get(FOODIE_SHOP_CART + ":" + useId);
        List<ShopCatBO> list;
        if (StringUtils.isNotBlank(shopCatJson)) {
            //redis中已经有购物车了
            list = JsonUtils.jsonToList(shopCatJson, ShopCatBO.class);
            //判断购物车中是否存在已有商品，如果有的话counts累加
            boolean isHaving=false;
            if(list==null||list.isEmpty()){
                return JsonResult.ok();
            }
            for (ShopCatBO redisShopCatBO : list) {
                String specId = redisShopCatBO.getSpecId();
                if (shopCatBO.getSpecId().equals(specId)) {
                    redisShopCatBO.setBuyCounts(redisShopCatBO.getBuyCounts()+shopCatBO.getBuyCounts());
                    isHaving=true;
                    break;
                }
            }
            if(!isHaving){
                list.add(shopCatBO);
            }
        }else{
            //redis中没有购物车
            list=new ArrayList<>();
            //直接添加到购物车中
            list.add(shopCatBO);
        }
        //覆盖现有redis中的购物车
        redisOperator.set(FOODIE_SHOP_CART + ":" + useId, JsonUtils.objectToJson(list));
        return JsonResult.ok();
    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "GET")
    @GetMapping("/del")
    public JsonResult del(@ApiParam(name = "useId", value = "用户id", required = true) @RequestParam String useId,
                          @ApiParam(name = "itemSpecId", value = "规格id", required = true) @RequestParam String itemSpecId,
                          HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(useId) || StringUtils.isBlank(useId)) {
            return JsonResult.errorMsg("");
        }
        //前端用户在登录的情况下，删除购物车商品，会同时在后端同步删除redis中的缓存
        String shopCartJson = redisOperator.get(FOODIE_SHOP_CART + ":" + useId);
        if(StringUtils.isNotBlank(shopCartJson)){
            //redis中已经有购物车了
            List<ShopCatBO> list=JsonUtils.jsonToList(shopCartJson, ShopCatBO.class);
            if(list==null||list.isEmpty()){
                return JsonResult.ok();
            }
            List<ShopCatBO> oldList=new ArrayList<>();
            oldList.addAll(list);
            //判断购物车中是否存在已有商品，如果有的话则删除
            for (ShopCatBO redisShopCatBO : oldList) {
                String specId = redisShopCatBO.getSpecId();
                if (itemSpecId.equals(specId)) {
                    list.remove(redisShopCatBO);
                    break;
                }
            }
            //覆盖现有redis中的购物车
            redisOperator.set(FOODIE_SHOP_CART + ":" + useId, JsonUtils.objectToJson(list));
        }
        return JsonResult.ok();
    }
}
