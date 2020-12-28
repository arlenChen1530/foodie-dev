package com.arlenchen.controller;

import com.arlenchen.appservice.AddressAppService;
import com.arlenchen.pojo.bo.AddressBO;
import com.arlenchen.utils.JsonResult;
import com.arlenchen.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 收货地址
 */
@Api(value = "地址相关", tags = {"地址相关的接口"})
@RestController
@RequestMapping("address")
public class AddressController {
    private final AddressAppService addressAppService;

    @Autowired
    public AddressController(AddressAppService addressAppService) {
        this.addressAppService = addressAppService;
    }

    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作：
     * 1.查询用户的所有收货地址列表
     * 2.新增收货地址
     * 3.删除收货地址
     * 4.修改收货地址
     * 5.设置默认地址
     */
    @ApiOperation(value = "根据用户id查询收货地址列表", notes = "根据用户id查询收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public JsonResult list(@ApiParam(name = "useId", value = "用户id", required = true) @RequestParam String useId,
                           HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(useId)) {
            return JsonResult.errorMsg("");
        }
        return JsonResult.ok(addressAppService.queryAll(useId));
    }

    @ApiOperation(value = "新增收货地址", notes = "新增收货地址", httpMethod = "POST")
    @PostMapping("/add")
    public JsonResult add(@ApiParam(name = "addressBO", value = "地址信息", required = true) @RequestBody AddressBO addressBO,
                          HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(addressBO.getUserId())) {
            return JsonResult.errorMsg("");
        }
        JsonResult jsonResult = checkAddress(addressBO);
        if (!jsonResult.isOK()) {
            return jsonResult;
        }
        addressAppService.addNewUserAddress(addressBO);
        return JsonResult.ok();
    }

    @ApiOperation(value = "修改收货地址", notes = "修改收货地址", httpMethod = "POST")
    @PostMapping("/update")
    public JsonResult update(@ApiParam(name = "addressBO", value = "地址信息", required = true) @RequestBody AddressBO addressBO,
                          HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(addressBO.getAddressId())) {
            return JsonResult.errorMsg("修改地址错误：addressId不能为空");
        }
        JsonResult jsonResult = checkAddress(addressBO);
        if (!jsonResult.isOK()) {
            return jsonResult;
        }
        addressAppService.updateNewUserAddress(addressBO);
        return JsonResult.ok();
    }

    @ApiOperation(value = "删除收货地址", notes = "删除收货地址", httpMethod = "POST")
    @PostMapping("/delete")
    public JsonResult delete(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "addressId", value = "地址id", required = true) @RequestParam String addressId,
                          HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userId)||StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("");
        }
        addressAppService.deleteAddress(userId,addressId);
        return JsonResult.ok();
    }
    @ApiOperation(value = "设置默认收货地址", notes = "设置默认收货地址", httpMethod = "POST")
    @PostMapping("/setdefalut")
    public JsonResult setDefault(
            @ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId,
            @ApiParam(name = "addressId", value = "地址id", required = true) @RequestParam String addressId,
                          HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userId)||StringUtils.isBlank(userId)) {
            return JsonResult.errorMsg("");
        }
        addressAppService.setDefaultAddress(userId,addressId);
        return JsonResult.ok();
    }

    /**
     * 校验地址必填项
     *
     * @param addressBO 地址
     * @return 验证结果
     */

    private JsonResult checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return JsonResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return JsonResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return JsonResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return JsonResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return JsonResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return JsonResult.errorMsg("收货地址信息不能为空");
        }

        return JsonResult.ok();
    }
}
