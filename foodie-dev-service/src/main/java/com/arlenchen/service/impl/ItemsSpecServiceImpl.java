package com.arlenchen.service.impl;

import com.arlenchen.mapper.ItemsSpecMapper;
import com.arlenchen.mapper.ItemsSpecMapperCustom;
import com.arlenchen.pojo.ItemsSpec;
import com.arlenchen.service.ItemsSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ItemsSpecServiceImpl implements ItemsSpecService {
    private final ItemsSpecMapper itemsSpecMapper;
    private final ItemsSpecMapperCustom itemsSpecMapperCustom;

    @Autowired
    public ItemsSpecServiceImpl(ItemsSpecMapper itemsSpecMapper, ItemsSpecMapperCustom itemsSpecMapperCustom) {
        this.itemsSpecMapper = itemsSpecMapper;
        this.itemsSpecMapperCustom = itemsSpecMapperCustom;
    }

    /**
     * 根据id查询商品规格
     *
     * @param id id
     * @return ItemsSpec
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsSpec queryById(String id) {
        return itemsSpecMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据ids查询商品规格
     *
     * @param ids id
     * @return List
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> ListByIds(String ids) {
        String[] idArr = ids.split(",");
        List<String> idList = new ArrayList<>();
        Collections.addAll(idList, idArr);
        return itemsSpecMapper.ListByIds(idList);
    }

    /**
     * 减少库存
     *
     * @param specId    规格id
     * @param buyCounts 购买数量
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void decreaseItemSpecStock(String specId, int buyCounts) {
        //synchronized 不推荐使用，集群无效，效能低下
        //锁数据库  不推荐使用 导致数据库性能低下
        // 分布式锁 推，没有分布式的时候，可以使用乐观锁，也就是通过sql语句做更新
        //LockUtil.getLock();--加锁
        //1.查询库存
//        int stock = 10;
        //2.判断库存，是否能减少到0一下
//        if (stock - buyCounts < 0) {
//提示库存不够
//        }
        //LockUtil.unLock();--解锁
        int result = itemsSpecMapperCustom.decreaseItemSpecStock(specId, buyCounts);
        if (result != 1) {
            throw new RuntimeException("订单创建失败，原因：库存不足！");
        }
    }
}
