package com.imooc.mall.controller;

import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Autowired
    UserService userService;

//    @GetMapping({"/test","test1"}) //多个URL指向同一个接口
    @GetMapping("/test")
    @ResponseBody //指定返回json格式
    public User personalPage(){
       return userService.getUser();
    }
    public void register(){

    }
}
