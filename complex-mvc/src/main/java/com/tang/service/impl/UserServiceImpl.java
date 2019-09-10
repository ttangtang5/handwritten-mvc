package com.tang.service.impl;

import com.tang.Constants;
import com.tang.dao.UserDao;
import com.tang.exception.ParameterException;
import com.tang.exception.ServiceException;
import com.tang.model.User;
import com.tang.service.UserService;
import com.tang.util.StringUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User login(String userName, String password) throws ParameterException, ServiceException {

        ParameterException parameterException = new ParameterException();

        if (StringUtil.checkString(userName)) {
            parameterException.addErrorFields(Constants.ERROR_USERNAME, Constants.ERROR_USERNAME_INFO);
        }

        if (StringUtil.checkString(password)) {
            parameterException.addErrorFields(Constants.ERROR_USERPASSWORD, Constants.ERROR_USERPASSWORD_INFO);
        }

        if (!parameterException.isEmpty()) {
            throw parameterException;
        }

        User user = userDao.getUserByUserName(userName);
        if (user != null && user.getPassword().equals(password)) {
            //The first judge the username and password is right
            return user;
        } else if(user != null) {
            //If program come in here, the password is wrong
            user.setPassword(null);
            throw new ServiceException(Constants.ERROR_USERPASSWORD_CODE, Constants.ERROR_USERPASSWORD_CODE_INFO);
        }
        //representative username is not exits.
        throw new ServiceException(Constants.ERROR_USERNAME_CODE, Constants.ERROR_USERNAME_CODE_INFO);
    }
}
