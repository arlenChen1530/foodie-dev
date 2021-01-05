package com.arlenchen.appservice.impl.center;

import com.arlenchen.appservice.center.CenterUserAppService;
import com.arlenchen.pojo.Users;
import com.arlenchen.pojo.bo.center.CenterUserBO;
import com.arlenchen.pojo.vo.UsersVO;
import com.arlenchen.service.center.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CenterUserAppServiceImpl implements CenterUserAppService {
    private final CenterUserService centerUserService;

    @Autowired
    public CenterUserAppServiceImpl(CenterUserService centerUserService) {
        this.centerUserService = centerUserService;
    }

    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UsersVO queryUsersInfo(String userId) {
        Users users = centerUserService.queryUsersInfo(userId);
        if (users != null) {
            UsersVO usersVO = new UsersVO();
            BeanUtils.copyProperties(users, usersVO);
            usersVO.setPassword(null);
            usersVO.setId(userId);
            return usersVO;
        }
        return null;
    }

    /**
     * 用户信息修改
     *
     * @param userId       用户ID
     * @param centerUserBO 用户信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UsersVO update(String userId, CenterUserBO centerUserBO) {
        centerUserService.update(userId, centerUserBO);
        return queryUsersInfo(userId);
    }

    /**
     * 用户头像上传
     *
     * @param userId  用户ID
     * @param faceUrl 头像
     */
    @Override
    public UsersVO uploadFace(String userId, String faceUrl) {
        CenterUserBO centerUserBO =new CenterUserBO();
        centerUserBO.setFace(faceUrl);
        centerUserService.update(userId, centerUserBO);
        return queryUsersInfo(userId);
    }

}
