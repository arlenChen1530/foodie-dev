package com.arlenchen.controller.center;

import com.arlenchen.appservice.center.CenterUserAppService;
import com.arlenchen.controller.BaseController;
import com.arlenchen.pojo.bo.center.CenterUserBO;
import com.arlenchen.pojo.vo.UsersVO;
import com.arlenchen.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;

@Api(value = "用户信息接口", tags = {"用户信息接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUsersController extends BaseController {
    private final CenterUserAppService centerUserAppService;

    @Autowired
    public CenterUsersController(CenterUserAppService centerUserAppService) {
        this.centerUserAppService = centerUserAppService;
    }

    @ApiOperation(value = "用户头像上传接口", notes = "用户头像上传接口", httpMethod = "POST")
    @PostMapping("/userFace")
    public JsonResult userFace(
            @ApiParam(value = "用户id", name = "userId", required = true) @RequestParam() String userId,
            @ApiParam(value = "用户头像", name = "multipartFile", required = true)
            @RequestParam(name = "multipartFile") MultipartFile multipartFile
    ) {
        //用户头像保存地址
        String fileSpace=IMG_USER_FACE_LOCATION.replace("/",File.separator);
        //在路径上为每个用户增加一个userId，用于区分不同用户上传
        String facePathPrefix=File.separator+userId;
        //开始文件上传
        if(multipartFile!=null){
            String fileName=multipartFile.getOriginalFilename();
            if(StringUtils.isNoneBlank(fileName)){

            }

        }else{
             return  JsonResult.errorMsg("文件不能为空");
        }
        return JsonResult.ok();
    }
    @ApiOperation(value = "用户信息修改接口", notes = "用户信息修改接口", httpMethod = "POST")
    @PostMapping("/update")
    public JsonResult update(
            @ApiParam(value = "用户id", name = "userId", required = true) @RequestParam() String userId,
            @ApiParam(value = "用户信息", name = "centerUserBO", required = true) @RequestBody @Valid CenterUserBO centerUserBO,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return JsonResult.errorMap(getErrors(result));
        }
        UsersVO userResult = centerUserAppService.update(userId, centerUserBO);
        //todo:后续修改，增加令牌token，整合到redis，分布式会话中，
        setNullVOProperty(userResult);
        return JsonResult.ok(userResult);
    }
}
