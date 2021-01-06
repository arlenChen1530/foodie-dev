package com.arlenchen.appservice.impl.center;

import com.arlenchen.appservice.center.MyCommentsAppService;
import com.arlenchen.pojo.OrderItems;
import com.arlenchen.pojo.bo.center.OrderItemCommentBO;
import com.arlenchen.pojo.vo.MyCommentVO;
import com.arlenchen.pojo.vo.OrderItemsVO;
import com.arlenchen.service.center.MyCommentsService;
import com.arlenchen.utils.PageGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author arlenchen
 */
@Service
public class MyCommentsAppServiceImpl implements MyCommentsAppService {
    private final MyCommentsService myCommentsService;

    @Autowired
    public MyCommentsAppServiceImpl(MyCommentsService myCommentsService) {
        this.myCommentsService = myCommentsService;
    }

    /**
     * 查询订单下的所有商品
     *
     * @param orderId 订单
     * @return List
     */
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<OrderItemsVO> queryPendingCommentList(String orderId) {
        List<OrderItemsVO> itemsVOList = new ArrayList<>();
        List<OrderItems> itemsList = myCommentsService.queryPendingCommentList(orderId);
        for (OrderItems orderItems : itemsList) {
            OrderItemsVO orderItemsVO = new OrderItemsVO();
            BeanUtils.copyProperties(orderItems, orderItemsVO);
            orderItemsVO.setId(orderItems.getId());
            itemsVOList.add(orderItemsVO);
        }
        return itemsVOList;
    }

    /**
     * 保存订单评价
     *
     * @param userId    用户
     * @param orderId   订单
     * @param itemsList 评价
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveComments(String userId, String orderId, List<OrderItemCommentBO> itemsList) {
        myCommentsService.saveComments(userId, orderId, itemsList);

    }
    /**
     * 历史评价
     *
     * @param userId   用户
     * @param page     分页
     * @param pageSize 分页
     * @return PageGridResult
     */
    @Override
    public PageGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        return myCommentsService.queryMyComments( userId,  page,  pageSize);
    }
}
