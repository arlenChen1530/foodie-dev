package com.arlenchen.controller;

import com.arlenchen.appservice.OrderAppService;
import com.arlenchen.pojo.bo.SubmitOrderBo;
import com.arlenchen.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 收货订单
 */
@Api(value = "订单相关", tags = {"订单相关的接口"})
@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderAppService orderAppService;

    @Autowired
    public OrderController(OrderAppService orderService) {
        this.orderAppService = orderService;
    }

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public JsonResult create(@ApiParam(name = "submitOrderBo", value = "用户下单", required = true) @RequestBody SubmitOrderBo submitOrderBo,
                           HttpServletRequest request, HttpServletResponse response) {
        orderAppService.createOrder(submitOrderBo);

        //1.创建订单
        //2.创建订单后，移除购物中已结算的商品
        //3.向支付中心发送订单，用于保存支付中心的订单数据
        return JsonResult.ok();
    }

}
