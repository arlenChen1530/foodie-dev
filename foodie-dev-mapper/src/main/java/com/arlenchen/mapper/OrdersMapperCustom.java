package com.arlenchen.mapper;

import com.arlenchen.pojo.vo.MyOrdersVO;
import com.arlenchen.pojo.vo.OrderStatusVO;
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

    /**
     * 查询各个状态的订单数量
     *
     * @param map map
     * @return 数量
     */
    int getMyOrderStatusCount(@Param("paramsMap") Map<String, Object> map);

    /**
     * 查询订单动向
     *
     * @param map map
     * @return 订单动向
     */
    List<OrderStatusVO> getMyOrderTend(@Param("paramsMap") Map<String, Object> map);
}
