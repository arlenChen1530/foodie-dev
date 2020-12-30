package com.arlenchen.controller;

import com.arlenchen.pojo.Users;
import com.arlenchen.pojo.vo.UsersVO;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BaseController {
    public static final String FOODIE_SHOP_CAT = "shopcat";
    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;
    public static final String paymentUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";
    //微信支付成功-->支付中心-->平台
    //                      回调通知的url
    String payResultUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";
 public  static  final  String IMG_USER_FACE_LOCATION="/Users/arlenchen/Documents/project/userFace";

    public void setNullProperty(Users userResult) {
        if(userResult!=null){
            userResult.setPassword(null);
            userResult.setMobile(null);
            userResult.setEmail(null);
            userResult.setCreatedTime(null);
            userResult.setUpdatedTime(null);
            userResult.setBirthday(null);
        }
    }

    public void setNullVOProperty(UsersVO userResult) {
        if(userResult!=null){
            userResult.setPassword(null);
            userResult.setMobile(null);
            userResult.setEmail(null);
            userResult.setCreatedTime(null);
            userResult.setUpdatedTime(null);
            userResult.setBirthday(null);
        }
    }

    public Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return map;
    }
}
