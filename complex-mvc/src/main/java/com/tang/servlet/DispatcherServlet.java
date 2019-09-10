package com.tang.servlet;

import com.tang.Constants;
import com.tang.common.*;
import com.tang.model.User;
import com.tang.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

/**
 *
 * @author 16
 * @description
 */
public class DispatcherServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private Map<String, ActionConfig> actionConfigs = new HashMap<String, ActionConfig>();

    private String suffix = ".action";

    public DispatcherServlet() {
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        String suffixFromConfi = config.getInitParameter("suffix");
        if (!StringUtil.checkString(suffixFromConfi)) {
            suffix = suffixFromConfi;
        }
        InputStream inputStream = null;

        try {
            inputStream = DispatcherServlet.class.getClassLoader().getResourceAsStream("action.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            Element element = document.getDocumentElement();

            NodeList actionNodes =  element.getElementsByTagName("action");

            if (actionNodes != null) {
                int actionLength = actionNodes.getLength();
                for (int i = 0;i < actionLength; i++) {
                    Element actionElement = (Element) actionNodes.item(i);
                    String action = actionElement.getAttribute("name");
                    String clsName = actionElement.getAttribute("class");
                    String method = actionElement.getAttribute("method");
                    String httpMethod = actionElement.getAttribute("httpMethod");
                    if (StringUtil.checkString(httpMethod)) {
                        httpMethod = "GET";
                    }
                    String[] httpMethods = httpMethod.split(",");  //
                    ActionConfig actionConfig = new ActionConfig();
                    actionConfig.setClsName(clsName);
                    actionConfig.setHttpMethod(httpMethods);
                    actionConfig.setMethodName(method);
                    actionConfig.setName(action);
                    for (String httpMethodsValue : httpMethods) {
                        actionConfigs.put(action + suffix + "#" + httpMethodsValue.toUpperCase(), actionConfig);
                    }
                    NodeList resultNodes = actionElement.getElementsByTagName("result");
                    if (resultNodes != null) {
                        int resultLength = resultNodes.getLength();
                        for (int j = 0 ; j < resultLength; j++) {
                            Element resultElement = (Element) resultNodes.item(j);
                            String resultName = resultElement.getAttribute("name");
                            String resultView = resultElement.getAttribute("view");
                            String resultRedirect = resultElement.getAttribute("redirect");
                            if (StringUtil.checkString(resultRedirect)) {
                                resultRedirect = "false";
                            }
                            ResultConfig resultConfig = new ResultConfig();
                            resultConfig.setName(resultName);
                            resultConfig.setView(resultView);
                            resultConfig.setRedirect(Boolean.parseBoolean(resultRedirect));
                            //TODO
                            String viewParameter = resultElement.getAttribute("viewParameter");
                            if (viewParameter != null) {
                                String[] viewParameterArr = viewParameter.split(",");
                                for (String viewParameterItem : viewParameterArr) {
                                    String[] viewParameterItemArr = viewParameterItem.split(":");
                                    String name = viewParameterItemArr[0].trim();
                                    String from = "attribute";
                                    if (viewParameterItemArr.length == 2) {
                                        from = viewParameterItemArr[1].trim();
                                    }
                                    ViewParameterConfig viewParameterConfig = new ViewParameterConfig();
                                    viewParameterConfig.setName(name);
                                    viewParameterConfig.setFrom(from);
                                    resultConfig.addViewParameterConfig(viewParameterConfig);
                                }
                            }
                            actionConfig.getResults().put(resultName, resultConfig);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCookie(String resource, HttpServletRequest request, HttpServletResponse response) {
        if ("login.action".equals(resource)) {
            //Get the all of cookie
            checkUserCookieIsExist(request);
        }
    }

    public void addCookie(String resource, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if ("loginPost.action".equals(resource)) {
            User user = (User) session.getAttribute(Constants.USER);
            if (user == null) {
                return;
            }
            //If pragraming in here ==> the user object must exits in session
            String userName = request.getParameter("userName");
            String password = request.getParameter("password");
            //Create user object
            //When the user is login successful, it instantiate cookie object.
            Cookie cookie = null;
            String remeberPWD = request.getParameter("remeberPWD");
            if (Constants.REMEBERME.equals(remeberPWD)) {
                //The cookie's content is 'id=username=password'
                cookie = new Cookie(Constants.USERCOOKIE,
                        user.getId() + "=" +
                        userName + "=" +
                        password + "=" +
                        user.getDisplayName());
                cookie.setMaxAge(86400);
                response.addCookie(cookie);
            } else if (Constants.NOREMEBERME.equals(remeberPWD) ) {
                //setMaxAge(0):The "userCookie" is delete;
                cookie = checkUserCookieIsExist(request);
                if (cookie != null) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    session.removeAttribute("userInfoOfCookie");
                    session.removeAttribute(Constants.USERCOOKIE);
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String resource = uri.substring(request.getContextPath().length()+1);
        //request.getServletPath();
        if (StringUtil.checkString(resource)) {
            resource = "login" + suffix;
        }
        HttpSession session = request.getSession();
        String requestMethod = request.getMethod();
        getCookie(resource, request, response);

        ActionConfig actionConfig = actionConfigs.get(resource + "#" + requestMethod);
        try {
            if (actionConfig != null) {
                Map<String, Object> cookiesMap = new HashMap<String, Object>();
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookiesValue : cookies) {
                        cookiesMap.put(cookiesValue.getName(), cookiesValue.getValue());
                    }
                }

                //178 - 197 line==> Put the session and request contents into sessionMap and requestMap
                Map<String, Object> sessionsMap = new HashMap<String, Object>();
                Enumeration<String> enumerationSession = session.getAttributeNames();
                while (enumerationSession.hasMoreElements()) {
                    String enumerationValue = enumerationSession.nextElement();
                    sessionsMap.put(enumerationValue, session.getAttribute(enumerationValue));
                }

                Map<String, String> requestMap = new HashMap<String, String>();
                Enumeration<String> enumerationRequest = request.getParameterNames();
                while (enumerationRequest.hasMoreElements()) {
                    String enumeraionValue = enumerationRequest.nextElement();
                    String paramRequest = request.getParameter(enumeraionValue);
                        requestMap.put(enumeraionValue, paramRequest);
                }

                Enumeration<String> enumerationRequestArritubute = request.getAttributeNames();
                while (enumerationRequestArritubute.hasMoreElements()) {
                    String enumeraionValue = enumerationRequestArritubute.nextElement();
                    requestMap.put(enumeraionValue, request.getAttribute(enumeraionValue).toString());
                }

                //199 - 214 ==> Reflect invoke method
                Class<?>[] param = new Class[2];
                param[0] = Map.class;
                param[1] = Map.class;

               /* Class cls = Class.forName(actionConfig.getClsName());
                Object obj = cls.newInstance();*/

                //According to class of "action.xml", use the BeanFactory to create object;
                Object obj = BeanFactory.getInstance().getBean(actionConfig.getClsName());
                Method method = obj.getClass().getMethod(actionConfig.getMethodName(), param);

                Object[] objects = new Object[2];
                objects[0] = sessionsMap;
                objects[1] = requestMap;
                ModelAndView modelAndView = (ModelAndView)method.invoke(obj, objects);

                //217 - 231 ==> Put the modelAndView's sessionMap and requestMap content into request and session
                Map<String, Object> fromControllerSession = modelAndView.getSessions();
                Set<String> sessionKeys = fromControllerSession.keySet();
                for (String key : sessionKeys) {
                    Object value = fromControllerSession.get(key);
                    if ("remove".equals(value)) { // remove value on session
                        session.removeAttribute(key);
                    } else {
                        session.setAttribute(key, fromControllerSession.get(key));
                    }
                }

                Map<String, Object> fromControllerRequest = modelAndView.getRequests();
                Set<String> requestKeys = fromControllerRequest.keySet();
                for (String key : requestKeys) {
                    request.setAttribute(key, fromControllerRequest.get(key));
                }

                addCookie(resource, request, response);

                //TODO Begin put together url
                String view = modelAndView.getView();

                ResultConfig resultConfig = actionConfig.getResults().get(view);
                if (resultConfig == null) {
                    if (modelAndView.isRedirect()) {
                        response.sendRedirect(request.getContextPath() + "/" + view);
                    } else {
                        request.getRequestDispatcher(view).forward(request, response);
                    }
                } else {
                    String resultView = resultConfig.getView();
                    if (resultConfig.isRedirect()) {
                        List<ViewParameterConfig> viewParameterConfigs = resultConfig.getViewParameterConfigs();
                        if (viewParameterConfigs == null ) {
                            response.sendRedirect(resultView);
                        } else {
                            StringBuilder sb = new StringBuilder();
                            for (ViewParameterConfig viewParameterConfig : viewParameterConfigs) {
                                String name = viewParameterConfig.getName();
                                String from = viewParameterConfig.getFrom();
                                String value = null;
                                if ("attribute".equals(from)) {
                                    value = (String) request.getAttribute(name);
                                } else if ("parameter".equals(from)) {
                                    value = request.getParameter(name);
                                } else if ("session".equals(from)) {
                                    value = (String) session.getAttribute(name);
                                } else {
                                    value = (String) request.getAttribute(name);
                                }

                                if (!StringUtil.checkString(value)) {
                                    sb.append(name + "=" + value + "&" );
                                }
                            }

                            if (resultView.indexOf("?") > 0) {
                                resultView += "&" + sb.toString();
                            } else {
                                resultView += "?" + sb.toString();
                            }
                            response.sendRedirect(resultView);
                        }
                    } else {
                        request.getRequestDispatcher(resultView).forward(request, response);
                        return;
                    }
                }
            } else {
                response.sendError(404);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    public Cookie checkUserCookieIsExist(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                //If the userCookie exits, I will gain the cookie information
                if (Constants.USERCOOKIE.equals(cookie.getName())) {
                    String cookieValue = cookie.getValue();
                    String[] userInfos = cookieValue.split("=");
                    User user = new User();
                    user.setId(Integer.parseInt(userInfos[0]));
                    user.setUserName(userInfos[1]);
                    user.setPassword(userInfos[2]);
                    user.setDisplayName(userInfos[3]);
                    session.setAttribute("userInfoOfCookie", user);
                    session.setAttribute(Constants.USERCOOKIE, "TEMP");
                    return cookie;
                }
            }
        }
        return null;
    }

}
