package com.arlenchen.appservice.impl.center;

import com.arlenchen.appservice.center.MyOrderAppService;
import com.arlenchen.pojo.Orders;
import com.arlenchen.pojo.vo.MyOrdersVO;
import com.arlenchen.service.center.MyOrdersService;
import com.arlenchen.utils.JsonResult;
import com.arlenchen.utils.PageGridResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author arlenchen
 */
@Service
public class MyOrderAppServiceImpl implements MyOrderAppService {

    private final MyOrdersService myOrdersService;

    @Autowired
    public MyOrderAppServiceImpl(MyOrdersService myOrdersService) {
        this.myOrdersService = myOrdersService;
    }


    /**
     * 查询用户订单
     *
     * @param userId      用户
     * @param page        分页
     * @param pageSize    分页
     * @param orderStatus 订单状态
     * @return List
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public PageGridResult queryOrderList(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        return myOrdersService.queryOrderList(userId, orderStatus, page, pageSize);
    }

    /**
     * 订单发货
     *
     * @param orderId 订单
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void ordersDeliver(String orderId) {
        myOrdersService.ordersDeliver(orderId);
    }

    /**
     * 订单确认收货
     *
     * @param orderId 订单
     * @return boolean
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean confirmReceive(String orderId) {
         return myOrdersService.confirmReceive(orderId);
    }

    /**
     * 用户删除订单
     *
     * @param orderId 订单
     * @param userId  用户
     * @return boolean
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean delete(String orderId, String userId) {
        return myOrdersService.delete(orderId, userId);
    }

    /**
     * 查询用户有该订单
     *
     * @param orderId 订单
     * @param userId  用户
     * @return Orders
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public JsonResult queryMyOrder(String orderId, String userId) {
        Orders orders= myOrdersService.queryMyOrder(orderId, userId);
        if (orders == null) {
            return JsonResult.errorMsg("订单不存在！");
        }
        MyOrdersVO myOrdersVO = new MyOrdersVO();
        BeanUtils.copyProperties(orders,myOrdersVO);
        return JsonResult.ok(myOrdersVO);
    }

}
