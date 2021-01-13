package com.imooc.mall.service.impl;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.UserMapper;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import com.imooc.mall.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

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
    public void register(String userName, String password) throws ImoocMallException {
        //查询用户名是否存在，不允许重名
        User result = userMapper.selectByName(userName);
        if (result != null){//首先需要判断返回结果是否为空及是否重名，但是返回为空的异常处理不应该在service层实现，而是应该由controller层来返回异常信息
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }

        //通过重名检测后，可以写入数据库
        User user = new User();
        user.setUsername(userName);
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
//        user.setPassword(password);
        int count = userMapper.insertSelective(user);//此时只修改username和password两个字段，故选择insertSelective方法
        if (count == 0){//正常插入，应该返回修改的行数
            throw new ImoocMallException(ImoocMallExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public User login(String userName,String password) throws ImoocMallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(userName,md5Password);
        if (user == null){
                throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;

    }
    @Override
    public void updateInformation(User user) throws ImoocMallException {
        int updateCount = userMapper.updateByPrimaryKeySelective(user);//该方法为修改一个字段；
        //使用新建user对象的原因：该方法在更新时，先判断对象传入的值，如果不为空，就把对象的值更新进去；如果直接使用session中的对象，那么其他字段都不会更新，包括update_time等，不符合逻辑，故使用新建对象；
        if (updateCount > 1){
            //此时判断条件为什么设置为大于1？因为我们只更改一条数据，故多于一条数据的都有问题；但是小于一条的情况只有0，当更新前后一样时，返回0；又因为用户处于已登录情况，很难出现其他情况
            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public boolean checkAdminRole(User user){
        //1是普通用户，2是管理员
        return user.getRole().equals(2);
    }
}
