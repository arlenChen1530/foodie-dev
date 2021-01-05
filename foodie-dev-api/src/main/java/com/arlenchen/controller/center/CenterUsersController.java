package com.arlenchen.controller.center;

import com.arlenchen.appservice.center.CenterUserAppService;
import com.arlenchen.controller.BaseController;
import com.arlenchen.pojo.bo.center.CenterUserBO;
import com.arlenchen.pojo.vo.UsersVO;
import com.arlenchen.resource.FileUpload;
import com.arlenchen.utils.DateUtil;
import com.arlenchen.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;

@Api(value = "用户信息接口", tags = {"用户信息接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUsersController extends BaseController {
    private final CenterUserAppService centerUserAppService;
    private final FileUpload fileUpload;

    @Autowired
    public CenterUsersController(CenterUserAppService centerUserAppService, FileUpload fileUpload) {
        this.centerUserAppService = centerUserAppService;
        this.fileUpload = fileUpload;
    }

    @ApiOperation(value = "用户头像上传接口", notes = "用户头像上传接口", httpMethod = "POST")
    @PostMapping("/uploadFace")
    public JsonResult uploadFace(
            @ApiParam(value = "用户id", name = "userId", required = true) @RequestParam() String userId,
            @ApiParam(value = "用户头像", name = "multipartFile", required = true)
            @RequestParam(name = "file") MultipartFile multipartFile
    ) {
        //用户头像保存地址
        String fileSpace = fileUpload.getImgUserFaceLocation().replace("/", File.separator);
        //在路径上为每个用户增加一个userId，用于区分不同用户上传
        String uploadPathPrefix = File.separator + userId;
        //开始文件上传
        if (multipartFile != null) {
            String fileName = multipartFile.getOriginalFilename();
            if (StringUtils.isNotEmpty(fileName)) {
                //文件重命名 "face.png"->["face","png"]
                String[] fileNameArr = fileName.split("\\.");
                //获取文件后缀名
                String suffix = fileNameArr[fileNameArr.length - 1];
                if (!suffix.equalsIgnoreCase("jpg") && !suffix.equalsIgnoreCase("jpeg") && !suffix.equalsIgnoreCase("png")) {
                    return JsonResult.errorMsg("图片格式不正确");
                }
                //face-{userId}.png
                //文件名重组，覆盖式上传，增量式：额外拼接当前时间
                String finalFileName = "face-" + userId + "." + suffix;
                String finalFilePath = fileSpace + uploadPathPrefix + File.separator + finalFileName;
                //用于给web服务访问的地址
                uploadPathPrefix += ("/" + finalFileName);
                File outFile = new File(finalFilePath);
                if (outFile.getParentFile() != null) {
                    //创建文件夹
                    outFile.getParentFile().mkdirs();
                }
                try (FileOutputStream outputStream = new FileOutputStream(outFile); InputStream inputStream = multipartFile.getInputStream()) {
                    IOUtils.copy(inputStream, outputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            return JsonResult.errorMsg("文件不能为空");
        }
        String imageServerUrl = fileUpload.getImageServerUrl();
        // 由于浏览器可能存在缓存的情况，所以在这里，我们需要加上时间戳来保证更新后的图片可以及时刷新
        String finalUserFaceUrl = imageServerUrl + uploadPathPrefix
                + "?t=" + DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        // 更新用户头像到数据库
        UsersVO userResult = centerUserAppService.uploadFace(userId, finalUserFaceUrl);
        setNullVOProperty(userResult);
        return JsonResult.ok(userResult);
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
