package com.arlenchen.appservice;

import com.arlenchen.pojo.bo.SubmitOrderBo;

public interface OrderAppService {
    /**
     * 用户下单
     * @param submitOrderBo 下单数据
     */
    void createOrder(SubmitOrderBo submitOrderBo);
}
