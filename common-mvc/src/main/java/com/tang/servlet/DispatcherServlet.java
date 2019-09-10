package com.tang.servlet;

import com.tang.common.ActionConfig;
import com.tang.common.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @FileName: DispatcherServlet
 * @Author: 16
 * @Date: 2018/8/29 17:05
 * @Description: 派发器  加载controller的配置，通过读取配置信息，调用对应不同的处理方法。
 */
public class DispatcherServlet extends HttpServlet {

    private Map<String, ActionConfig> actionConfigMap = new HashMap<String, ActionConfig>();

    @Override
    public void init(){
        InputStream is = null;

        is = this.getClass().getClassLoader().getResourceAsStream("action.xml");

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = null;
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(is);
            //将配置文件加载进容器
            NodeList nodeList = document.getElementsByTagName("action");
            if(nodeList != null){
                for (int i = 0;i < nodeList.getLength(); i++){
                    Element element = (Element) nodeList.item(i);
                    String name = element.getAttribute("name");
                    String cls = element.getAttribute("class");
                    String method = element.getAttribute("method");

                    ActionConfig actionConfig = new ActionConfig();
                    actionConfig.setName(name);
                    actionConfig.setCls(cls);
                    actionConfig.setMethod(method);

                    actionConfigMap.put(name, actionConfig);
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取serlvet映射的路径 /springmvc/demo.action  servletUrl:/demo.action
        String servletUrl = req.getServletPath();
        String resources = servletUrl.substring(1);
        if(actionConfigMap.containsKey(resources)){
            //用于存放request和session携带的参数 传入Controller方法
            Map<String, Object> requestValue = new HashMap<String, Object>();
            Map<String, Object> sessionValue = new HashMap<String, Object>();

            //放入测试值
            req.setAttribute("att","value");

            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()){
                String element = parameterNames.nextElement();
                requestValue.put(element, req.getParameter(element));
            }

            Enumeration<String> attributeNames = req.getAttributeNames();
            while (attributeNames.hasMoreElements()){
                String element = attributeNames.nextElement();
                requestValue.put(element, req.getAttribute(element));
            }

            HttpSession session = req.getSession();
            session.setAttribute("sessionH","value");
            Enumeration<String> sessionAttributeNames = session.getAttributeNames();
            while (sessionAttributeNames.hasMoreElements()){
                String element = sessionAttributeNames.nextElement();
                sessionValue.put(element, session.getAttribute(element));
            }

            ActionConfig actionConfig = actionConfigMap.get(resources);
            String cls = actionConfig.getCls();
            String methodName = actionConfig.getMethod();

            try {
                Class<?> controllerCls = Class.forName(cls);
                Object controllerObj = controllerCls.newInstance();
                //TODO 可以设计的更灵活一些
                Class[] parameterTypes = new Class[2];
                //parameterTypes[0] = Map.class;
                parameterTypes[0] = HttpServletRequest.class;
                //parameterTypes[1] = Map.class;
                parameterTypes[1] = HttpServletResponse.class;

                Method method = controllerCls.getMethod(methodName, parameterTypes);
                //ModelAndView modelAndView = (ModelAndView) method.invoke(controllerObj, requestValue, sessionValue);
                ModelAndView modelAndView = (ModelAndView) method.invoke(controllerObj, req, resp);

                //将controller值的处理重新放入req,resp
                Map<String, Object> controllerReq = modelAndView.getRequestValue();
                Map<String, Object> controllerSession = modelAndView.getSessionValue();

                Set<String> reqSet = controllerReq.keySet();
                for (String key : reqSet) {
                    req.setAttribute(key, controllerReq.get(key));
                }

                Set<String> sessionSet = controllerSession.keySet();
                for(String key : sessionSet){
                    //判断是否移除
                    String value = (String) controllerSession.get(key);
                    if("remove".equals(value)){
                        session.removeAttribute(key);
                    }else{
                        session.setAttribute(key, value);
                    }
                }

                if(modelAndView.isRedirect()){
                    resp.sendRedirect(modelAndView.getView());
                }else{
                    req.getRequestDispatcher(modelAndView.getView()).forward(req,resp);
                }

            }  catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }else{
            //没有处理方法返回404
            resp.sendError(404);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
