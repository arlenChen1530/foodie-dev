package com.arlenchen.controller.center;

import com.arlenchen.appservice.center.MyOrderAppService;
import com.arlenchen.controller.BaseController;
import com.arlenchen.pojo.vo.OrderStatusCountsVO;
import com.arlenchen.pojo.vo.OrderStatusVO;
import com.arlenchen.utils.CommonUtils;
import com.arlenchen.utils.JsonResult;
import com.arlenchen.utils.PageGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("myorders")
public class MyOrderController extends BaseController {
    private final MyOrderAppService myOrderAppService;

    @Autowired
    public MyOrderController(MyOrderAppService myOrderAppService) {
        this.myOrderAppService = myOrderAppService;
    }

    @ApiOperation(value = "订单管理", notes = "订单管理", httpMethod = "POST")
    @PostMapping("/query")
    public JsonResult queryOrderList(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态") @RequestParam(required = false) Integer orderStatus,
            @ApiParam(name = "page", value = "分页") @RequestParam(required = false, defaultValue = "0") Integer page,
            @ApiParam(name = "pageSize", value = "分页") @RequestParam(required = false, defaultValue = "20") Integer pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        if (page == null) {
            page = 0;
        }
        if (pageSize == null) {
            pageSize = CommonUtils.PAGE_SIZE;
        }
        PageGridResult pageGridResult = myOrderAppService.queryOrderList(userId, orderStatus, page, pageSize);
        return JsonResult.ok(pageGridResult);
    }

    @ApiOperation(value = "订单发货", notes = "订单发货", httpMethod = "GET")
    @GetMapping("/deliver")
    public JsonResult ordersDeliver(
            @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam String orderId,
            HttpServletRequest request, HttpServletResponse response) {
        myOrderAppService.ordersDeliver(orderId);
        return JsonResult.ok();
    }

    @ApiOperation(value = "订单确认收货", notes = "订单确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public JsonResult confirmReceive(
            @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            HttpServletRequest request, HttpServletResponse response) {
        //验证用户对应的订单是否存在
        JsonResult checkOrderResult = myOrderAppService.queryMyOrder(orderId, userId);
        if (!checkOrderResult.isOk()) {
            return checkOrderResult;
        }
        boolean confirmResult = myOrderAppService.confirmReceive(orderId);
        if (!confirmResult) {
            return JsonResult.errorMsg("订单确认收货失败！");
        }
        return JsonResult.ok();
    }

    @ApiOperation(value = "用户删除订单", notes = "用户删除订单", httpMethod = "GET")
    @GetMapping("/delete")
    public JsonResult delete(
            @ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            HttpServletRequest request, HttpServletResponse response) {
        //验证用户对应的订单是否存在
        JsonResult checkOrderResult = myOrderAppService.queryMyOrder(orderId, userId);
        if (!checkOrderResult.isOk()) {
            return checkOrderResult;
        }
        boolean deleteResult = myOrderAppService.delete(orderId, userId);
        if (!deleteResult) {
            return JsonResult.errorMsg("订单删除失败！");
        }
        return JsonResult.ok();
    }

    @ApiOperation(value = "查询各个状态的订单数量", notes = "查询各个状态的订单数量", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public JsonResult statusCounts(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            HttpServletRequest request, HttpServletResponse response) {
        OrderStatusCountsVO orderStatusCountsVO = myOrderAppService.getMyOrderStatusCount(userId);
        return JsonResult.ok(orderStatusCountsVO);
    }

    @ApiOperation(value = "查询订单动向", notes = "查询订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    public JsonResult getMyOrderTrend(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "page", value = "分页") @RequestParam(required = false, defaultValue = "0") Integer page,
            @ApiParam(name = "pageSize", value = "分页") @RequestParam(required = false, defaultValue = "20") Integer pageSize,
            HttpServletRequest request, HttpServletResponse response) {
        PageGridResult  pageGridResult = myOrderAppService.getMyOrderTend(userId,page,pageSize);
        return JsonResult.ok(pageGridResult);
    }

}
