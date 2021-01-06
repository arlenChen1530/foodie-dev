package com.arlenchen.mapper;

import com.arlenchen.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author arlenchen
 */
public interface OrdersMapperCustom {
    /**
     * 查询订单列表
     *
     * @param map map
     * @return List
     */
    List<MyOrdersVO> queryOrderList(@Param("paramsMap") Map<String, Object> map);

}
