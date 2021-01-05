package com.arlenchen.exception;

import com.arlenchen.utils.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class CustomExceptionHandler {
    /**
     * 上传文件超过500k，捕获异常：MaxUploadSizeExceededException
     *
     * @param maxUploadSizeExceededException 捕获异常
     * @return JsonResult
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public JsonResult handlerMaxUploadFile(MaxUploadSizeExceededException maxUploadSizeExceededException) {
        return JsonResult.errorMsg("上传文件大小不能超过500k,请压缩图片或者降低图片质量再上传!");

    }
}
