package com.arlenchen.enums;

/**
 * @author arlenchen
 */

public enum YesOrNo {
    /**
     * yes
     */
    YES(1,"yes"),
    /**
     * no
     */
    NO(0,"no");
    public  final  Integer type;
    public  final  String value;
    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
