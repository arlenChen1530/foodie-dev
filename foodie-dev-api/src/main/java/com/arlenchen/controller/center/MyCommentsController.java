package com.arlenchen.controller.center;

import com.arlenchen.appservice.center.MyCommentsAppService;
import com.arlenchen.appservice.center.MyOrderAppService;
import com.arlenchen.controller.BaseController;
import com.arlenchen.enums.YesOrNo;
import com.arlenchen.pojo.bo.center.OrderItemCommentBO;
import com.arlenchen.pojo.vo.MyOrdersVO;
import com.arlenchen.pojo.vo.OrderItemsVO;
import com.arlenchen.utils.JsonResult;
import com.arlenchen.utils.PageGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 个人中心订单
 *
 * @author arlenchen
 */
@Api(value = "个人中心订单", tags = {"个人中心订单相关的接口"})
@RestController
@RequestMapping("mycomments")
public class MyCommentsController extends BaseController {
    private final MyCommentsAppService myCommentsAppService;
    private final MyOrderAppService myOrderAppService;

    @Autowired
    public MyCommentsController(MyCommentsAppService myCommentsAppService, MyOrderAppService myOrderAppService) {
        this.myCommentsAppService = myCommentsAppService;
        this.myOrderAppService = myOrderAppService;
    }

    @ApiOperation(value = "获取订单商品列表", notes = "获取订单商品列表", httpMethod = "POST")
    @PostMapping("/pending")
    public JsonResult pending(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id") @RequestParam(required = false) String orderId,
            HttpServletRequest request, HttpServletResponse response) {
        JsonResult checkUserOrderResult = myOrderAppService.queryMyOrder(orderId, userId);
        if (!checkUserOrderResult.isOk()) {
            return checkUserOrderResult;
        }
        MyOrdersVO myOrdersVO = (MyOrdersVO) checkUserOrderResult.getData();
        if (myOrdersVO.getIsComment().compareTo(YesOrNo.YES.type) == 0) {
            return JsonResult.errorMsg("该笔订单已经评价");
        }

        List<OrderItemsVO> list = myCommentsAppService.queryPendingCommentList(orderId);
        return JsonResult.ok(list);
    }

    @ApiOperation(value = "保存订单评价", notes = "保存订单评价", httpMethod = "POST")
    @PostMapping("/saveList")
    public JsonResult saveList(
            @ApiParam(name = "userId", value = "用户id", required = true)@RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id") @RequestParam String orderId,
            @ApiParam(name = "itemsList", value = "订单评价") @RequestBody List<OrderItemCommentBO> itemsList,
            HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isEmpty(userId)) {
            return JsonResult.errorMsg("用户id不能为空");
        }
        if (StringUtils.isEmpty(orderId)) {
            return JsonResult.errorMsg("订单id不能为空");
        }
        JsonResult checkUserOrderResult = myOrderAppService.queryMyOrder(orderId, userId);
        if (!checkUserOrderResult.isOk()) {
            return checkUserOrderResult;
        }
        if (itemsList == null || itemsList.isEmpty()) {
            return JsonResult.errorMsg("评论内容不能为空！");
        }
        myCommentsAppService.saveComments(userId, orderId, itemsList);
        return JsonResult.ok();
    }

    @ApiOperation(value = "历史评价", notes = "历史评价", httpMethod = "POST")
    @PostMapping("/query")
    public JsonResult queryMyComments(
            @ApiParam(name = "userId", value = "用户id", required = true)@RequestParam String userId,
            @ApiParam(name = "page", value = "分页") @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页") @RequestParam Integer pageSize,
            HttpServletRequest request, HttpServletResponse response) {
       PageGridResult pageGridResult = myCommentsAppService.queryMyComments(userId, page, pageSize);
        return JsonResult.ok(pageGridResult);
    }

}
