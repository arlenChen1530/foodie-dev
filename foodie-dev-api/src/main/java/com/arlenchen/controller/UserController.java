package com.arlenchen.controller;

import com.arlenchen.appservice.UserAppService;
import com.arlenchen.pojo.bo.ShopCatBO;
import com.arlenchen.pojo.bo.UserBO;
import com.arlenchen.pojo.vo.UsersVO;
import com.arlenchen.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author arlenchen
 */
@Api(value = "用户注册", tags = "用户注册相关接口")
@RestController
@RequestMapping("passport")
public class UserController extends BaseController {
    private final UserAppService userService;
    private final RedisOperator redisOperator;

    @Autowired
    public UserController(UserAppService userService, RedisOperator redisOperator) {
        this.userService = userService;
        this.redisOperator = redisOperator;
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
        if (!jsonResult.isOk()) {
            return jsonResult;
        }
        UsersVO usersVO = (UsersVO) jsonResult.getData();
        setNullVoProperty(usersVO);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVO), true);
        // TODO 生成用户token，存入redis会话
        //  同步购物车数据
        syncShopCartData(usersVO.getId(), request, response);
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
        JsonResult jsonResult = userService.login(userName, MD5Utils.getMD5Str(passWord));
        if (!jsonResult.isOk() || jsonResult.getData() == null) {
            return JsonResult.errorMsg("用户名或密码不正确");
        }
        UsersVO usersVO = (UsersVO) jsonResult.getData();
        setNullVoProperty(usersVO);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(usersVO), true);
        // TODO 生成用户token，存入redis会话
        //  同步购物车数据
        syncShopCartData(usersVO.getId(), request, response);
        return JsonResult.ok(usersVO);
    }

    /**
     * 注册登录成功后，同步cookie和redis中的购物车数据
     */
    private void syncShopCartData(String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        /*
         * 1.redis中无数据，如果cookie中的购物车为空，那么这个时候不做任何处理
         *                如果cookie中的购物车不为空，此时直接放入redis中
         * 2.redis中有数据，如果cookie中的购物车为空，那么直接把redis中的购物车覆盖本地cookie
         *                如果cookie中的购物车不为空，如果cookie中的某个商品在redis中存在，
         *                则以cookie为主，删除redis中的商品，把cookie中的商品直接覆盖redis中（参考京东）
         * 3.同步到redis中去了以后，覆盖本地cookie购物车的数据，保证本地购物车的数据是同步最新的
         */
        //1.从redis中获取购物车
        String shopCartRedisJsonStr = redisOperator.get(FOODIE_SHOP_CART + ":" + userId);
        //2.从cookie中获取购物车
        String shopCartCookieJsonStr = CookieUtils.getCookieValue(request, FOODIE_SHOP_CART + ":" + userId);
        if (StringUtils.isBlank(shopCartRedisJsonStr)) {
            //1.redis中无数据
            // 如果cookie中的购物车为空，那么这个时候不做任何处理,如果cookie中的购物车不为空，此时直接放入redis中
            if (StringUtils.isNotBlank(shopCartCookieJsonStr)) {
                redisOperator.set(FOODIE_SHOP_CART + ":" + userId, shopCartCookieJsonStr);
            }
        } else {
            //2.redis中有数据，
            if (StringUtils.isBlank(shopCartCookieJsonStr)) {
                //如果cookie中的购物车为空，那么直接把redis中的购物车覆盖本地cookie
                CookieUtils.setCookie(request, response, FOODIE_SHOP_CART + ":" + userId, shopCartRedisJsonStr, true);
            } else {
                //如果cookie中的购物车不为空，如果cookie中的某个商品在redis中存在，则以cookie为主，删除redis中的商品，把cookie中的商品直接覆盖redis中（参考京东）
                /*
                 * 2.1.已经存在的，把cookie中对应的数量，覆盖redis
                 * 2.2.该项商品标记为待删除，统一放入一个待删除的list
                 * 2.3.从cookie中清理所有的待删除list
                 * 2.4.合并redis和cookie中的数据
                 * 2.5.更新到redis和cookie中
                 */
                List<ShopCatBO> shopCartRedisList = JsonUtils.jsonToList(shopCartRedisJsonStr, ShopCatBO.class);
                List<ShopCatBO> shopCartCookieList = JsonUtils.jsonToList(shopCartCookieJsonStr, ShopCatBO.class);
                List<ShopCatBO> deleteList = new ArrayList<>();
                if(shopCartRedisList==null||shopCartRedisList.isEmpty()||shopCartCookieList==null||shopCartCookieList.isEmpty()){
                     return;
                }
                for (ShopCatBO shopCartRedis : shopCartRedisList) {
                    String redisSpecId = shopCartRedis.getSpecId();
                    for (ShopCatBO shopCartCookie : shopCartCookieList) {
                        if (shopCartCookie.getSpecId().equals(redisSpecId)) {
                            //2.1.覆盖购买数量，不累加，京东
                            shopCartRedis.setBuyCounts(shopCartCookie.getBuyCounts());
                            //2.2.把shopCartCookie放入待删除列表，用于最后的删除与合并
                            deleteList.add(shopCartCookie);

                        }
                    }
                }
                //2.3.从cookie中清理所有的待删除list
                shopCartCookieList.removeAll(deleteList);
                //2.4.合并redis和cookie中的数据
                shopCartRedisList.addAll(shopCartCookieList);
                //2.5.更新到redis和cookie中
                redisOperator.set(FOODIE_SHOP_CART + ":" + userId, JsonUtils.objectToJson(shopCartRedisList));
                CookieUtils.setCookie(request, response, FOODIE_SHOP_CART + ":" + userId, JsonUtils.objectToJson(shopCartRedisList), true);
            }
        }
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public JsonResult logout(@RequestParam String userId,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");
        //  用户退出登录，需要清空购物车
        CookieUtils.deleteCookie(request, response, FOODIE_SHOP_CART + ":" + userId);    
        // TODO 分布式会话中需要清除用户数据

        return JsonResult.ok();
    }

}
