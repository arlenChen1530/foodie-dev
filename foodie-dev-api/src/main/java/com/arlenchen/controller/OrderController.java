package com.arlenchen.controller;

import com.arlenchen.appservice.OrderAppService;
import com.arlenchen.enums.OrderStatusEnum;
import com.arlenchen.enums.PayMethod;
import com.arlenchen.pojo.bo.MerchantOrdersBO;
import com.arlenchen.pojo.bo.ShopCatBO;
import com.arlenchen.pojo.bo.SubmitOrderBO;
import com.arlenchen.pojo.vo.OrderStatusVO;
import com.arlenchen.pojo.vo.OrderVO;
import com.arlenchen.utils.CookieUtils;
import com.arlenchen.utils.JsonResult;
import com.arlenchen.utils.JsonUtils;
import com.arlenchen.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 收货订单
 *
 * @author arlenchen
 */
@Api(value = "订单相关", tags = {"订单相关的接口"})
@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {
    private final OrderAppService orderAppService;

    private final RestTemplate template;
    private final RedisOperator redisOperator;

    @Autowired
    public OrderController(OrderAppService orderService, RestTemplate template, RedisOperator redisOperator) {
        this.orderAppService = orderService;
        this.template = template;
        this.redisOperator = redisOperator;
    }

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public JsonResult create(@ApiParam(name = "submitOrderBo", value = "用户下单", required = true) @RequestBody SubmitOrderBO submitOrderBo,
                             HttpServletRequest request, HttpServletResponse response) {
        if (submitOrderBo.getPayMethod().equals(PayMethod.WE_CHAT.type) && submitOrderBo.getPayMethod().equals(PayMethod.A_LI_PAY.type)) {
            return JsonResult.errorMsg("支付方式不支持！");
        }

        String shopCartJson = redisOperator.get(FOODIE_SHOP_CART + ":" + submitOrderBo.getUserId());
        if (StringUtils.isBlank(shopCartJson)) {
            return JsonResult.errorMsg("购物车数据不正确");
        }
        List<ShopCatBO> list= JsonUtils.jsonToList(shopCartJson, ShopCatBO.class);
        //1.创建订单
        OrderVO orderVO = orderAppService.createOrder(list,submitOrderBo);
        MerchantOrdersBO merchantOrdersBO = orderVO.getMerchantOrdersBO();
        merchantOrdersBO.setReturnUrl(payResultUrl);
        //2.创建订单后，移除购物中已结算的商品
        //清理覆盖现有的redis汇总的购物数据
        if(list!=null&&!list.isEmpty()){
            list.removeAll(orderVO.getToBeRemoveShopCartList());
        }
        //整合redis后，完善购物车中已结算商品清除，并且同步到前端的cookie中
        redisOperator.set(FOODIE_SHOP_CART + ":" + submitOrderBo.getUserId(),JsonUtils.objectToJson(list));
        CookieUtils.setCookie(request,response,FOODIE_SHOP_CART + ":" + submitOrderBo.getUserId(),JsonUtils.objectToJson(list),true);
        //3.向支付中心发送订单，用于保存支付中心的订单数据
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("userId", "test");
        httpHeaders.add("passWord", "123456");
        HttpEntity<MerchantOrdersBO> requestEntity = new HttpEntity<>(merchantOrdersBO, httpHeaders);
        ResponseEntity<JsonResult> resultResponseEntity = template.postForEntity(PAYMENT_URL, requestEntity, JsonResult.class);
        JsonResult payResult = resultResponseEntity.getBody();
        if (payResult == null || !payResult.isOk()) {
            return JsonResult.errorMsg("支付中心创建订单失败，请联系管理员");
        }
        return JsonResult.ok(orderVO.getOrderId());
    }

    @ApiOperation(value = "用户订单支付结果通知", notes = "用户订单支付结果通知", httpMethod = "POST")
    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(@ApiParam(name = "merchantOrderId", value = "订单id", required = true) @RequestParam String merchantOrderId) {
        orderAppService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @ApiOperation(value = "查询订单是否支付成功", notes = "查询订单是否支付成功", httpMethod = "POST")
    @PostMapping("/getPaidOrderInfo")
    public JsonResult getPaidOrderInfo(@ApiParam(name = "orderId", value = "订单id", required = true) @RequestParam String orderId) {
        OrderStatusVO orderStatusVO = orderAppService.getPaidOrderInfo(orderId);
        return JsonResult.ok(orderStatusVO);
    }


}
