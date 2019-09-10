package com.tang.controller;

import com.tang.service.UserService;
import com.tang.utils.BeanResource;
import com.tang.utils.ModelAndView;
import java.util.Map;

/**
 * @Description
 * @Author RLY
 * @Date 2018/11/29 9:45
 * @Version 1.0
 **/
public class UserController {

    //private UserService userService = (UserService) ApplicationContext.getBeanFactory().getBean("userService");
    @BeanResource(name = "userService")
    private UserService userService;

    public ModelAndView login(Map<String, Object> requestParam, Map<String, Object> sessionParam){
        userService.login();
        ModelAndView modelAndView = new ModelAndView();
        requestParam.put("name", "user1");
        requestParam.put("password", "hk123");
        modelAndView.setRequest(requestParam);
        modelAndView.setSession(sessionParam);
        modelAndView.setRedirect(true);
        modelAndView.setView("../index.html");
        return modelAndView;
    }
}
