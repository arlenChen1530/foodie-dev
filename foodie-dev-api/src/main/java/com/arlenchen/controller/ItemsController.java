package com.arlenchen.controller;

import com.arlenchen.pojo.Items;
import com.arlenchen.pojo.ItemsImg;
import com.arlenchen.pojo.ItemsParam;
import com.arlenchen.pojo.ItemsSpec;
import com.arlenchen.pojo.vo.ItemInfoVO;
import com.arlenchen.service.ItemService;
import com.arlenchen.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "商品接口", tags = "商品信息展示的相关接口")
@RestController
@RequestMapping("items")
public class ItemsController {
    private final ItemService itemService;

    public ItemsController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JsonResult userNameIsExist(@ApiParam(name = "itemId", value = "商品Id", required = true)  @PathVariable() String itemId) {
        if (itemId == null) {
            return JsonResult.errorMsg("");
        }
        Items items = itemService.queryItemById(itemId);
        List<ItemsImg>  itemsImgList=itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList =itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam =itemService.queryItemParam(itemId);
        ItemInfoVO itemsInfoVO = new ItemInfoVO();
        itemsInfoVO.setItems(items);
        itemsInfoVO.setItemImgList(itemsImgList);
        itemsInfoVO.setItemSpecList(itemsSpecList);
        itemsInfoVO.setItemParams(itemsParam);
        return JsonResult.ok(itemsInfoVO);
    }
}
