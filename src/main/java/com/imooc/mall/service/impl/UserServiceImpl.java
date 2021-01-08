package com.imooc.mall.service.impl;

import com.imooc.mall.model.dao.UserMapper;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    //产生红线，却能正常运行的原因为在启动类中添加了MapperScan注解告诉了mybatis包的位置，IDE却依然无法识别
    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }
}
