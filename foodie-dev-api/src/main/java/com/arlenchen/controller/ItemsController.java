package com.arlenchen.controller;

import com.arlenchen.pojo.Items;
import com.arlenchen.pojo.ItemsImg;
import com.arlenchen.pojo.ItemsParam;
import com.arlenchen.pojo.ItemsSpec;
import com.arlenchen.pojo.vo.CommentLevelCountsVO;
import com.arlenchen.pojo.vo.ItemInfoVO;
import com.arlenchen.pojo.vo.ShopCatVO;
import com.arlenchen.service.ItemService;
import com.arlenchen.utils.JsonResult;
import com.arlenchen.utils.PageGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品接口", tags = "商品信息展示的相关接口")
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController {
    private final ItemService itemService;

    public ItemsController(ItemService itemService) {
        this.itemService = itemService;
    }

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JsonResult userNameIsExist(@ApiParam(name = "itemId", value = "商品Id", required = true) @PathVariable() String itemId) {
        if (itemId == null) {
            return JsonResult.errorMsg("");
        }
        Items items = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        ItemInfoVO itemsInfoVO = new ItemInfoVO();
        itemsInfoVO.setItems(items);
        itemsInfoVO.setItemImgList(itemsImgList);
        itemsInfoVO.setItemSpecList(itemsSpecList);
        itemsInfoVO.setItemParams(itemsParam);
        return JsonResult.ok(itemsInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JsonResult commentLevel(@ApiParam(name = "itemId", value = "商品Id", required = true) @RequestParam String itemId) {
        if (itemId == null) {
            return JsonResult.errorMsg("");
        }
        CommentLevelCountsVO commentLevelCountsVO = itemService.queryCommentCounts(itemId);
        return JsonResult.ok(commentLevelCountsVO);
    }

    @ApiOperation(value = "查询商品评价", notes = "查询商品评价", httpMethod = "GET")
    @GetMapping("/comments")
    public JsonResult comments(@ApiParam(name = "itemId", value = "商品Id", required = true) @RequestParam String itemId,
                               @ApiParam(name = "level", value = "级别") @RequestParam(required = false) Integer level,
                               @ApiParam(name = "page", value = "页数") @RequestParam(required = false) Integer page,
                               @ApiParam(name = "pageSize", value = "每页数量") @RequestParam(required = false) Integer pageSize) {
        if (itemId == null) {
            return JsonResult.errorMsg("");
        }
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PageGridResult pageGridResult = itemService.queryPageComments(itemId, level, page, pageSize);
        return JsonResult.ok(pageGridResult);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public JsonResult search(@ApiParam(name = "keywords", value = "关键字", required = true) @RequestParam(required = false) String keywords,
                             @ApiParam(name = "sort", value = "排序方式") @RequestParam(required = false) String sort,
                             @ApiParam(name = "page", value = "页数") @RequestParam(required = false) Integer page,
                             @ApiParam(name = "pageSize", value = "每页数量") @RequestParam(required = false) Integer pageSize) {
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PageGridResult pageGridResult = itemService.searchItems(keywords, sort, page, pageSize);
        return JsonResult.ok(pageGridResult);
    }

    @ApiOperation(value = "根据三级分类查询商品", notes = "根据三级分类查询商品", httpMethod = "GET")
    @GetMapping("/catItems")
    public JsonResult catItems(@ApiParam(name = "catId", value = "三级分类Id", required = true) @RequestParam(required = false) Integer catId,
                               @ApiParam(name = "sort", value = "排序方式") @RequestParam(required = false) String sort,
                               @ApiParam(name = "page", value = "页数") @RequestParam(required = false) Integer page,
                               @ApiParam(name = "pageSize", value = "每页数量") @RequestParam(required = false) Integer pageSize) {
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PageGridResult pageGridResult = itemService.searchItemsByThirdCat(catId, sort, page, pageSize);
        return JsonResult.ok(pageGridResult);
    }

    @ApiOperation(value = "根据规格id查询最新的商品数据", notes = "根据规格id查询最新的商品数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public JsonResult refresh(@ApiParam(name = "specIds", value = "规格id", required = true,example = "('1001','1002')") @RequestParam() String specIds) {
        List<ShopCatVO> list = itemService.queryItemsBySpecIds(specIds);
        return JsonResult.ok(list);
    }
}
