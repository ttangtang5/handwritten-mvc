package com.tang.service;

import com.tang.common.BeanFactory;
import com.tang.dao.UserDao;
import com.tang.entity.User;

import java.util.List;

/**
 * @FileName: UserService
 * @Author: 16
 * @Date: 2018/8/29 20:29
 * @Description:
 */
public class UserService {

    private UserDao userDao = (UserDao) BeanFactory.getBeanFactory().getBean("userDao");

    public List<User> getUser(){
        return userDao.selectAllUser();
    }
}
