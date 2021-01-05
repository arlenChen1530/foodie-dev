package com.arlenchen.appservice;


import com.arlenchen.pojo.Users;
import com.arlenchen.pojo.bo.UserBO;
import com.arlenchen.utils.JsonResult;

/**
 * @author arlenchen
 */
public interface UserAppService {
    /**
     *  查询用户名是否存在
     * @param userName 用户名
     * @return  boolean
     */
    boolean queryUserNameIsExist(String userName);

    /**
     * 创建用户
     *
     * @param userBO 用户
     * @return 用户
     */
    JsonResult createUses(UserBO userBO);

    /**
     * 登录用户
     * @param userName 用户名
     * @param passWord 密码
     * @return 用户
     */
    JsonResult login(String userName, String passWord);
}
