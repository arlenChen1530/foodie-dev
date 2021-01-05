package com.arlenchen.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:face_upload_dev.properties")
public class FileUpload {
    /**
     * 用户头像上传图片保存路径
     */
    private String imgUserFaceLocation;
    /**
     * 用户头像上传图片服务地址
     */
    private String imageServerUrl;

    public String getImgUserFaceLocation() {
        return imgUserFaceLocation;
    }

    public void setImgUserFaceLocation(String imgUserFaceLocation) {
        this.imgUserFaceLocation = imgUserFaceLocation;
    }

    public String getImageServerUrl() {
        return imageServerUrl;
    }

    public void setImageServerUrl(String imageServerUrl) {
        this.imageServerUrl = imageServerUrl;
    }
}
