package com.arlenchen.controller.center;

import com.arlenchen.appservice.center.CenterUserAppService;
import com.arlenchen.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author arlenchen
 */
@Api(value = "用户中心", tags = {"用户中心相关接口"})
@RestController
@RequestMapping("center")
public class CenterController {
    private final CenterUserAppService centerUserAppService;

    @Autowired
    public CenterController(CenterUserAppService centerUserAppService) {
        this.centerUserAppService = centerUserAppService;
    }

    @ApiOperation(value = "用户查询信息接口", notes = "用户查询信息接口", httpMethod = "GET")
    @GetMapping("/userInfo")
    public JsonResult usersInfo(@ApiParam(value = "用户id", name = "userId", required = true) @RequestParam() String userId) {
        return JsonResult.ok(centerUserAppService.queryUsersInfo(userId));
    }

}
