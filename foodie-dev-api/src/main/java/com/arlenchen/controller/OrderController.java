package com.arlenchen.controller;

import com.arlenchen.appservice.OrderAppService;
import com.arlenchen.enums.OrderStatusEnum;
import com.arlenchen.pojo.bo.SubmitOrderBo;
import com.arlenchen.utils.CookieUtils;
import com.arlenchen.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 收货订单
 */
@Api(value = "订单相关", tags = {"订单相关的接口"})
@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {
    private final OrderAppService orderAppService;

    @Autowired
    public OrderController(OrderAppService orderService) {
        this.orderAppService = orderService;
    }

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public JsonResult create(@ApiParam(name = "submitOrderBo", value = "用户下单", required = true) @RequestBody SubmitOrderBo submitOrderBo,
                             HttpServletRequest request, HttpServletResponse response) {
        //1.创建订单
        String orderId = orderAppService.createOrder(submitOrderBo);
        //2.创建订单后，移除购物中已结算的商品
        //todo:整合redis后，完善购物车中已结算商品清除，并且同步到前端的cookie中
//        CookieUtils.setCookie(request,response,"FOODIE_SHOP_CAT","",true);
        //3.向支付中心发送订单，用于保存支付中心的订单数据
        return JsonResult.ok(orderId);
    }

    @ApiOperation(value = "用户订单支付结果通知", notes = "用户订单支付结果通知", httpMethod = "POST")
    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(@ApiParam(name = "merchantOrderId", value = "订单id", required = true) @RequestParam String merchantOrderId) {
        orderAppService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

}
