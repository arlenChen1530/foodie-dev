package com.arlenchen.service;


import com.arlenchen.pojo.Users;
import com.arlenchen.pojo.bo.UserBO;

public interface UserService {
    boolean queryUserNameIsExist(String userName);

    /**
     * 创建用户
     *
     * @param userBO 用户
     * @return 用户
     */
    Users createUses(UserBO userBO);

    /**
     * 登录用户
     * @param userName 用户名
     * @param passWord 密码
     * @return 用户
     */
    Users login(String userName, String passWord);
}
