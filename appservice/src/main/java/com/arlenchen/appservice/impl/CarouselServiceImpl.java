package com.arlenchen.appservice.impl;

import com.arlenchen.appservice.CarouselAppService;
import com.arlenchen.mapper.CarouselMapper;
import com.arlenchen.pojo.Carousel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CarouselServiceImpl  implements CarouselAppService {

    @Autowired
    private CarouselMapper carouselMapper;

    /**
     * 查询所有可显示的轮播图
     * @param isShow 可显示的
     * @return List
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example=new Example(Carousel.class);
        example.orderBy("sort").desc();
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("isShow",isShow);
        return carouselMapper.selectByExample(example);
    }
}
