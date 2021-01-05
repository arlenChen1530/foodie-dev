package com.arlenchen.enums;

/**
 * 商品评价登记枚举
 * @author arlenchen
 */
public enum CommentLevel {
    /**
     * 好评
     */
     good(1,"好评"),
    /**
     * 中评
     */
     normal(2,"中评"),
    /**
     * 差评
     */
     bad(3,"差评");
     public  final  Integer  value;
     public  final  String name;

    CommentLevel(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
