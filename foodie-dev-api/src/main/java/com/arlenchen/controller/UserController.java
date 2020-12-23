package com.arlenchen.controller;

import com.arlenchen.pojo.Users;
import com.arlenchen.pojo.bo.UserBO;
import com.arlenchen.service.UserService;
import com.arlenchen.utils.JsonResult;
import com.arlenchen.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户注册",tags = "用户注册相关接口")
@RestController
@RequestMapping("passport")
public class UserController {
     final static  Logger logger =LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
@ApiOperation(value = "用户是否存在",notes = "用户是否存在",httpMethod = "GET")
    @GetMapping("/userNameIsExist")
    public JsonResult userNameIsExist(String userName) {
        if (StringUtils.isBlank(userName)) {
            return JsonResult.errorMsg("用户名不能为空");
        }
        boolean isExist = userService.queryUserNameIsExist(userName);
        if (isExist) {
            return JsonResult.errorMsg("用户名已存在");
        }
        return JsonResult.ok();
    }

    @ApiOperation(value = "用户注册",notes = "用户注册",httpMethod = "POST")
    @PostMapping("/register")
    public JsonResult register(@RequestBody UserBO userBO) {
        String userName = userBO.getUserName();
        String passWord = userBO.getPassWord();
        String confirmPassWord = userBO.getConfirmPassWord();
        //0.判断用户名和密码不能为空
        if (StringUtils.isBlank(userName)) {
            return JsonResult.errorMsg("用户名不能为空");
        }
        if (StringUtils.isBlank(passWord)) {
            return JsonResult.errorMsg("密码不能为空");
        }
        if (StringUtils.isBlank(confirmPassWord)) {
            return JsonResult.errorMsg("确认密码不能为空");
        }
        //1.查询用户名是否存在
        boolean isExist = userService.queryUserNameIsExist(userName);
        if (isExist) {
            return JsonResult.errorMsg("用户名已存在");
        }
        //2.判断密码长度是否大于6位
        if (passWord.length() < 6) {
            return JsonResult.errorMsg("密码长度必须大于6位");
        }
        //3.判断两次密码是否一致
        if (!passWord.equals(confirmPassWord)) {
            return JsonResult.errorMsg("两次密码不一致");
        }
        //4. 实现注册
        userService.createUses(userBO);

        return JsonResult.ok();
    }
    @ApiOperation(value = "用户登录",notes = "用户登录",httpMethod = "POST")
    @PostMapping("/login")
    public JsonResult login(@RequestBody UserBO userBO)  throws  Exception{
        String userName = userBO.getUserName();
        String passWord = userBO.getPassWord();
        Users users = userService.login(userName, MD5Utils.getMD5Str(passWord));
        if(users==null){
            return JsonResult.errorMsg("用户名或密码不正确");
        }
        logger.info("用户登录");
        return JsonResult.ok(users);
    }
}
