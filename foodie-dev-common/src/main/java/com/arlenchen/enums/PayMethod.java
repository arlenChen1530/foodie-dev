package com.arlenchen.enums;

/**
 * @author arlenchen
 */

public enum PayMethod {
    /**
     * 微信
     */
    WE_CHAT(0,"微信"),
    /**
     *支付宝
     */
     A_LI_PAY(1,"支付宝");
     public  final  Integer  type;
     public  final  String value;

    PayMethod(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
