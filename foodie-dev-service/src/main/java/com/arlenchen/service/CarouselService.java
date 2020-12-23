package com.arlenchen.service;

import com.arlenchen.pojo.Carousel;

import java.util.List;

public interface CarouselService {
    /**
     * 查询所有可显示的轮播图
     * @param isShow 可显示的
     * @return List
     */
    List<Carousel> queryAll(Integer isShow);
}
