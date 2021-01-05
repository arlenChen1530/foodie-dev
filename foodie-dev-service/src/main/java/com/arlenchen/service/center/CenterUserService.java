package com.arlenchen.service.center;

import com.arlenchen.pojo.Users;
import com.arlenchen.pojo.bo.center.CenterUserBO;

/**
 * @author arlenchen
 */
public interface CenterUserService {
    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    Users queryUsersInfo(String userId);
    /**
     * 用户信息修改
     *
     * @param userId       用户ID
     * @param centerUserBO 用户信息
     */
    void update(String userId, CenterUserBO centerUserBO);
}
