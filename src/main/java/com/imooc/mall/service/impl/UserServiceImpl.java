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

    @Override
    public void register(String userName, String password) {
        //查询用户名是否存在，不允许重名
        User result = userMapper.selectByName(userName);
        if (result != null){//首先需要判断返回结果是否为空，但是返回为空的异常处理不应该在service层实现，而是应该由controller层来返回异常信息

        }
    }
}
