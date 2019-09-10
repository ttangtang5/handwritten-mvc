package com.tang.dao;

import com.tang.model.User;

public interface UserDao {

    User getUserByUserName(String userName);
}
