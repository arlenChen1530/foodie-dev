package com.arlenchen.controller;

import com.arlenchen.appservice.UserAppService;
import com.arlenchen.pojo.Users;
import com.arlenchen.pojo.bo.UserBO;
import com.arlenchen.pojo.vo.UsersVO;
import com.arlenchen.utils.CookieUtils;
import com.arlenchen.utils.JsonResult;
import com.arlenchen.utils.JsonUtils;
import com.arlenchen.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author arlenchen
 */
@Api(value = "用户注册", tags = "用户注册相关接口")
@RestController
@RequestMapping("passport")
public class UserController  extends  BaseController{
    private final UserAppService userService;

    @Autowired
    public UserController(UserAppService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "用户是否存在", notes = "用户是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public JsonResult usernameIsExist(String userName) {
        // 1. 判断用户名不能为空
        if (StringUtils.isBlank(userName)) {
            return JsonResult.errorMsg("用户名不能为空");
        }
        // 2. 查找注册的用户名是否存在
        boolean isExist = userService.queryUserNameIsExist(userName);
        if (isExist) {
            return JsonResult.errorMsg("用户名已存在");
        }
        // 3. 请求成功，用户名没有重复
        return JsonResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public JsonResult register(@RequestBody UserBO userBO,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        String userName = userBO.getUsername();
        String passWord = userBO.getPassword();
        String confirmPassWord = userBO.getConfirmPassword();
        //0.判断用户名和密码不能为空
        if (StringUtils.isBlank(userName) ||
                StringUtils.isBlank(passWord) ||
                StringUtils.isBlank(confirmPassWord)) {
            return JsonResult.errorMsg("用户名或密码不能为空");
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
        JsonResult jsonResult = userService.createUses(userBO);
        if(!jsonResult.isOk()){
             return  jsonResult;
        }
        UsersVO usersVO =(UsersVO)jsonResult.getData();
        setNullVoProperty(usersVO);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVO), true);
        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据
        return JsonResult.ok();
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public JsonResult login(@RequestBody UserBO userBO,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        String userName = userBO.getUsername();
        // 0. 判断用户名和密码必须不为空
        String passWord = userBO.getPassword();
        if (StringUtils.isBlank(userName) ||
                StringUtils.isBlank(passWord)) {
            return JsonResult.errorMsg("用户名或密码不能为空");
        }
        // 1. 实现登录
        JsonResult jsonResult= userService.login(userName, MD5Utils.getMD5Str(passWord));
        if (!jsonResult.isOk()||jsonResult.getData() == null) {
            return JsonResult.errorMsg("用户名或密码不正确");
        }
        UsersVO usersVO =(UsersVO)jsonResult.getData();
        setNullVoProperty(usersVO);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVO), true);
        // TODO 生成用户token，存入redis会话
        // TODO 同步购物车数据
        return JsonResult.ok(usersVO);
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public JsonResult logout(@RequestParam String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");
        // TODO 用户退出登录，需要清空购物车
        // TODO 分布式会话中需要清除用户数据

        return JsonResult.ok();
    }

}
