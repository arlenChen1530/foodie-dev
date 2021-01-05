package com.arlenchen.enums;

/**
 * @author arlenchen
 */

public enum Sex {
    /**
     * 女
     */
     woman(0,"女"),
    /**
     *男
     */
     man(1,""),
    /**
     *保密
     */
     secret(2,"保密");
     public  final  Integer  type;
     public  final  String value;

    Sex(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
