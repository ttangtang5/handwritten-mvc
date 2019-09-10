package com.tang.dao;

import com.tang.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: UserDao
 * @Author: 16
 * @Date: 2018/8/29 20:26
 * @Description:
 */
public class UserDao {

    public List<User> selectAllUser(){
        List<User> list = new ArrayList<User>();
        list.add(new User("1","a"));
        list.add(new User("2","b"));
        list.add(new User("3","c"));
        list.add(new User("4","d"));
        return list;
    }
}
