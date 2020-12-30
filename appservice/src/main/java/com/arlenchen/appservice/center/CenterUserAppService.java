package com.arlenchen.appservice.center;

import com.arlenchen.pojo.bo.center.CenterUserBO;
import com.arlenchen.pojo.vo.UsersVO;

public interface CenterUserAppService {
    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    UsersVO queryUsersInfo(String userId);

    /**
     * 用户信息修改
     *
     * @param userId       用户ID
     * @param centerUserBO 用户信息
     */
    UsersVO update(String userId, CenterUserBO centerUserBO);
}
