package com.imooc.mall.model.dao;

import com.imooc.mall.model.pojo.User;
import org.springframework.stereotype.Repository;

@Repository//添加注解以告诉IDE这个接口为指定资源
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
