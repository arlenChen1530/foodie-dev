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

/**
 * @author arlenchen
 */
@Service
public class CarouselServiceImpl  implements CarouselAppService {

    private final CarouselMapper carouselMapper;
@Autowired
    public CarouselServiceImpl(CarouselMapper carouselMapper) {
        this.carouselMapper = carouselMapper;
    }

    /**
     * 查询所有可显示的轮播图
     * @param isShow 可显示的
     * @return List
     */
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example=new Example(Carousel.class);
        example.orderBy("sort").desc();
        Example.Criteria criteria= example.createCriteria();
        criteria.andEqualTo("isShow",isShow);
        return carouselMapper.selectByExample(example);
    }
}
