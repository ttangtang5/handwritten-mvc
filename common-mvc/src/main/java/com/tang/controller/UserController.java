package com.tang.controller;

import com.tang.common.BeanFactory;
import com.tang.common.ModelAndView;
import com.tang.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @FileName: UserController
 * @Author: 16
 * @Date: 2018/8/29 20:33
 * @Description: 控制层
 */
public class UserController {

    private UserService userService = (UserService) BeanFactory.getBeanFactory().getBean("userService");

    public ModelAndView getUser(Map<String,Object> request,Map<String,Object> session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("index.jsp");
        modelAndView.setRedirect(false);
        request.put("newRequest", "request");
        session.put("newSession", "session");
        modelAndView.setRequestValue(request);
        modelAndView.setSessionValue(session);
        return modelAndView;
    }

    public ModelAndView addUser(HttpServletRequest req, HttpServletResponse resp){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("index.jsp");
        modelAndView.setRedirect(false);
        System.out.println("添加用户");
        return modelAndView;
    }
}
