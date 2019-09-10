package com.tang.service;

import com.tang.exception.ParameterException;
import com.tang.exception.ServiceException;
import com.tang.model.User;

public interface UserService {

    public User login(String userName, String password) throws ParameterException, ServiceException;
}
