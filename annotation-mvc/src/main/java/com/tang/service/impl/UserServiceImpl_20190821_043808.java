package com.tang.service.impl;

import com.tang.service.UserService;
import com.tang.utils.Bean;

/**
 * @Description
 * @Author RLY
 * @Date 2018/11/28 11:10
 * @Version 1.0
 **/
@Bean(name = "userService", singleton = true)
public class UserServiceImpl implements UserService {

    @Override
    public void login() {
        System.out.println(" login loading ");
    }
}
