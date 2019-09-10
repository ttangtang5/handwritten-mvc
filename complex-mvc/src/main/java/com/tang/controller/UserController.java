package com.tang.controller;

import com.tang.Constants;
import com.tang.common.ModelAndView;
import com.tang.exception.ParameterException;
import com.tang.exception.ServiceException;
import com.tang.model.User;
import com.tang.service.UserService;
import com.tang.util.StringUtil;
import java.util.Map;

public class UserController {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ModelAndView requestLoginResource(Map<String, Object> sessionsMap,
                                             Map<String, String> requestMap) {
        //HttpServletRequest request = (HttpServletRequest)AppContext.getInstance().getObjects().get("request");
        //HttpServletResponse response = (HttpServletResponse)AppContext.getInstance().getObjects().get("response");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.removeSessionsAttribute(Constants.ERROR_MESSAGE);

        User user = (User) sessionsMap.get(Constants.USER);

        String logoutFalg = (String) sessionsMap.get("action");

        //It have user session  how to don't care about cookie?
        if (user != null && user.getPassword() != null && !"logout".equals(logoutFalg)) {
            modelAndView.setRedirect(true);
            modelAndView.setView("success");
        } else if ("logout".equals(logoutFalg)) {
            modelAndView.removeSessionsAttribute(Constants.USER);
            modelAndView.removeSessionsAttribute("action");
            modelAndView.setRedirect(false);
            modelAndView.setView("error");
        }

        String go = requestMap.get("go");
        if (!StringUtil.checkString(go) && user != null) {
            modelAndView.setRedirect(true);
            modelAndView.setView("/" +go);
        } else if (user == null) {
            modelAndView.setRedirect(false);
            modelAndView.setView("error");
        }
        return modelAndView;
    }

    public ModelAndView logout(Map<String, Object> sessionsMap,
            Map<String, String> requestMap) {
        //Goto doPost method\
        ModelAndView modelAndView = new ModelAndView();
        if ("logout".equals(requestMap.get("action"))) {
            // deal with logout
            modelAndView.removeSessionsAttribute(Constants.USER);
            modelAndView.removeSessionsAttribute(Constants.ERROR_MESSAGE);
            // use the sendRedirect type goto login page
            modelAndView.setSessionAttribute("action", "logout");
            modelAndView.setRedirect(true);
            modelAndView.setView("login.action");
            return modelAndView;
        }
        //Deal with login
        modelAndView.setRedirect(true);
        modelAndView.setView("success");
        return modelAndView;
    }

    public ModelAndView loginPost(Map<String, Object> sessionsMap,
            Map<String, String> requestMap) {
        ModelAndView modelAndView = new ModelAndView();
        String userName =  requestMap.get("userName");
        String password =  requestMap.get("password");
        //Create user object
        User user = null;

        //When the user is login successful, it instantiate cookie object.
        try {
            user = userService.login(userName, password);
            modelAndView.setSessionAttribute(Constants.USER, user);
            modelAndView.removeSessionsAttribute(Constants.ERROR_MESSAGE);
            modelAndView.setRedirect(true);
            modelAndView.setView("loginSuccess");
            return modelAndView;
        } catch (ParameterException parameterException) {
            //if the program come in here, the parameter must is null
            Map<String,Object> errorFields = parameterException.getErrorFields();
            modelAndView.setRequestAttribute(Constants.ERROR_MESSAGE, errorFields);
            modelAndView.setRedirect(false);
            modelAndView.setView("loginError");
            return modelAndView;
        } catch (ServiceException serviceException) {
            String message = serviceException.getMessage();
            int errorCode = serviceException.getCode();
            //if the program come in here, the username or password is mistake.
            if (errorCode == 1001 ) {//1001 show the password is mistake
                //Instantiate user object
                user = new User();
                user.setUserName(userName);
                modelAndView.setSessionAttribute("userInfoOfCookie", user);
            }

            if (errorCode == 1000) {
                modelAndView.removeSessionsAttribute("userInfoOfCookie");
            }
            modelAndView.setSessionAttribute(Constants.ERROR_MESSAGE, message);
            modelAndView.setRedirect(false);
            modelAndView.setView("loginError");
            return modelAndView;
        }
    }
}
