package com.arlenchen.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    public static final String FOODIE_SHOP_CAT = "shopcat";
    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;
    public  static  final  String paymentUrl="http://localhost:8088/orders/notifyMerchantOrderPaid";
    //微信支付成功-->支付中心-->平台
    //                      回调通知的url
    String payResultUrl="http://localhost:8088/orders/notifyMerchantOrderPaid";
}
