package com.arlenchen.appservice.impl;

import com.arlenchen.appservice.AddressAppService;
import com.arlenchen.enums.YesOrNo;
import com.arlenchen.mapper.UserAddressMapper;
import com.arlenchen.pojo.UserAddress;
import com.arlenchen.pojo.bo.AddressBO;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressAppService {
    private final UserAddressMapper userAddressMapper;

    private final Sid sid;

    @Autowired
    public AddressServiceImpl(UserAddressMapper userAddressMapper, Sid sid) {
        this.userAddressMapper = userAddressMapper;
        this.sid = sid;
    }

    /**
     * 根据用户id查询所有收货地址
     *
     * @param userId 用户id
     * @return List
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }

    /**
     * 用户新增收货地址
     *
     * @param addressBO 收货地址
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBO addressBO) {
        //1.判断用户是否存在地址，如果不存在，则新增为"默认地址"
        List<UserAddress> list = this.queryAll(addressBO.getUserId());
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(sid.nextShort());
        userAddress.setIsDefault(list == null || list.isEmpty() ? 1 : 0);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        //2.保存地址
        userAddressMapper.insert(userAddress);
    }

    /**
     * 用户修改收货地址
     *
     * @param addressBO 收货地址
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateNewUserAddress(AddressBO addressBO) {
        //1.判断用户是否存在地址，如果不存在，则新增为"默认地址"
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(addressBO.getAddressId());
        userAddress.setUpdatedTime(new Date());
        //2.保存地址
        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    /**
     * 根据用户ID和地址id删除用户地址
     *
     * @param userId    用户id
     * @param addressId 地址id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);
        userAddressMapper.delete(userAddress);
    }

    /**
     * 根据用户ID和地址id设置地址为默认地址
     *
     * @param userId    用户id
     * @param addressId 地址id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void setDefaultAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
         List<UserAddress> list =userAddressMapper.select(userAddress);
         for(UserAddress ua : list){
             ua.setIsDefault(YesOrNo.NO.type);
             userAddressMapper.updateByPrimaryKeySelective(ua);
         }
        userAddress.setId(addressId);
        userAddress.setIsDefault(YesOrNo.YES.type);
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }
    /**
     * 根据用户ID和地址id查询地址
     *
     * @param userId    用户id
     * @param addressId 地址id
     * @return 地址
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);
        return userAddressMapper.selectOne(userAddress);
    }
}
