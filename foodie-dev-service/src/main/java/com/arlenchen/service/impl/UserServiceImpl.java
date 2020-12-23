package com.arlenchen.service.impl;

import com.arlenchen.enums.Sex;
import com.arlenchen.mapper.UsersMapper;
import com.arlenchen.pojo.Users;
import com.arlenchen.pojo.bo.UserBO;
import com.arlenchen.service.UserService;
import com.arlenchen.utils.DateUtil;
import com.arlenchen.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Sid sid;

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUserNameIsExist(String userName) {
        Example example =new Example(Users.class);
        Example.Criteria userCriteria=example.createCriteria();
        userCriteria.andEqualTo("username",userName);
        Users resultUser=usersMapper.selectOneByExample(example);
        return resultUser!=null;
    }

    /**
     * 创建用户
     * @param userBO 用户
     * @return 用户
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUses(UserBO userBO)  {
        Users users =new Users();
        users.setUsername(userBO.getUserName());
        try {
            users.setPassword(MD5Utils.getMD5Str(userBO.getPassWord()));
        } catch (Exception e) {
            e.getMessage();
        }
        users.setNickname(userBO.getUserName());
        users.setFace(USER_FACE);
        users.setBirthday(DateUtil.stringToDate("1900-01-01"));
        users.setSex(Sex.secret.type);
        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());
        users.setId(sid.nextShort());
        usersMapper.insert(users);
        return users;
    }

    /**
     * 登录用户
     * @param userName 用户名
     * @param passWord 密码
     * @return 用户
     */
    @Override
    public Users login(String userName, String passWord) {
        Example example =new Example(Users.class);
        Example.Criteria userCriteria=example.createCriteria();
        userCriteria.andEqualTo("username",userName);
        userCriteria.andEqualTo("password",passWord);
        return usersMapper.selectOneByExample(example);
    }

}
