package com.arlenchen.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户对象bo",description = "从客户端，由用户传入的数据封装在此entity中")
public class UserBO {
    @ApiModelProperty(value = "用户名",name = "userName",required = true,example = "111")
    String userName;
    @ApiModelProperty(value = "密码",name = "passWord",required = true,example = "123456")
    String passWord;
    @ApiModelProperty(value = "确认密码",name = "confirmPassWord",required = true,example = "123456")
    String confirmPassWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getConfirmPassWord() {
        return confirmPassWord;
    }

    public void setConfirmPassWord(String confirmPassWord) {
        this.confirmPassWord = confirmPassWord;
    }
}
