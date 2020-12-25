package com.arlenchen.service;

import com.arlenchen.pojo.UserAddress;
import com.arlenchen.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {
    /**
     * 根据用户id查询所有收货地址
     *
     * @param userId 用户id
     * @return List
     */
    List<UserAddress> queryAll(String userId);

    /**
     * 用户新增收货地址
     *
     * @param addressBO 收货地址
     */
    void addNewUserAddress(AddressBO addressBO);

    /**
     * 用户修改收货地址
     *
     * @param addressBO 收货地址
     */
    void updateNewUserAddress(AddressBO addressBO);

    /**
     * 根据用户ID和地址id删除用户地址
     *
     * @param userId    用户id
     * @param addressId 地址id
     */
    void deleteAddress(String userId, String addressId);

    /**
     * 根据用户ID和地址id设置地址为默认地址
     *
     * @param userId    用户id
     * @param addressId 地址id
     */
    void setDefaultAddress(String userId, String addressId);
}
