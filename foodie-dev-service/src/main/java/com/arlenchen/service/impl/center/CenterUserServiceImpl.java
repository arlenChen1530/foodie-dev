package com.arlenchen.service.impl.center;

import com.arlenchen.mapper.UsersMapper;
import com.arlenchen.pojo.Users;
import com.arlenchen.pojo.bo.center.CenterUserBO;
import com.arlenchen.service.center.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CenterUserServiceImpl implements CenterUserService {
    private final UsersMapper usersMapper;

    @Autowired
    public CenterUserServiceImpl(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
    }

    /**
     * 根据用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUsersInfo(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }

    /**
     * 用户信息修改
     *
     * @param userId       用户ID
     * @param centerUserBO 用户信息
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void update(String userId, CenterUserBO centerUserBO) {
        Users users =new Users();
        BeanUtils.copyProperties(centerUserBO,users);
        users.setId(userId);
        users.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(users);
    }
}
