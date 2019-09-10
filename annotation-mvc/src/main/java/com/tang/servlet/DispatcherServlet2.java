package com.tang.servlet;

import com.google.common.collect.Lists;
import com.tang.utils.Action;
import com.tang.utils.ApplicationContext;
import com.tang.utils.FileUitls;
import com.tang.utils.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 请求派发器
 * @Author RLY
 * @Date 2018/11/28 10:27
 * @Version 1.0
 **/
public class DispatcherServlet2 extends HttpServlet {

    private Map<String, Action>  actionConfig = new HashMap<String, Action>();

    @Override
    public void init() throws ServletException {
        Action action = null;
        //初始化派发容器
        InputStream is = DispatcherServlet2.class.getResourceAsStream("/action.xml");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(is);
            NodeList nodeList = document.getElementsByTagName("action");

            int len = nodeList.getLength();
            for(int i = 0; i < len; i++){
                Node item = nodeList.item(i);
                NamedNodeMap namedNodeMap = item.getAttributes();
                String path = namedNodeMap.getNamedItem("path").getNodeValue();
                String id = namedNodeMap.getNamedItem("id").getNodeValue();
                String cls = namedNodeMap.getNamedItem("class").getNodeValue();
                String methodName = namedNodeMap.getNamedItem("method").getNodeValue();

                action = new Action();
                action.setPath(path);
                action.setName(id);
                action.setActionCls(cls);
                action.setMethodName(methodName);

                actionConfig.put(path, action);
            }

             //初始化spring容器
            ApplicationContext applicationContext = ApplicationContext.getBeanFactory();

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求路径
        String uri = req.getRequestURI();
        String resource = uri.substring(req.getContextPath().length());

        //判断是不是有个handler
        if(actionConfig.containsKey(resource)){
            HttpSession session = req.getSession();
            Action action = actionConfig.get(resource);

            String key = null;
            //取出req中的参数值
            Map<String, Object> sessionParam = new HashMap<String, Object>();
            Enumeration<String> sessionAttributeNames = session.getAttributeNames();
            while (sessionAttributeNames.hasMoreElements()){
                key = sessionAttributeNames.nextElement();
                sessionParam.put(key, session.getAttribute(key));
            }

            Map<String, Object> requestParam = new HashMap<String, Object>();
            Enumeration<String> parameterNames = req.getParameterNames();
            while (parameterNames.hasMoreElements()){
                key = parameterNames.nextElement();
                requestParam.put(key, req.getParameter(key));
            }

            Enumeration<String> attributeNames = req.getParameterNames();
            while (attributeNames.hasMoreElements()){
                key = attributeNames.nextElement();
                requestParam.put(key, req.getParameter(key));
            }

            //反射调用handler
            try {
                Class objCls = Class.forName(action.getActionCls());
                Object obj = objCls.newInstance();

                Class[] paramCls = new Class[]{Map.class,Map.class};
                Method method = objCls.getMethod(action.getMethodName(), paramCls);
                Object[] objects = new Object[]{requestParam, sessionParam};
                ModelAndView modelAndView = (ModelAndView) method.invoke(obj, objects);

                //取出参数存放至容器
                Map<String, Object> reqView = modelAndView.getRequest();
                for(String keyView : reqView.keySet()){
                    Object o = reqView.get(keyView);
                    req.setAttribute(keyView, o);
                }

                Map<String, Object> sessionView = modelAndView.getSession();
                for (String keyView : sessionView.keySet()) {
                    Object o = sessionView.get(keyView);
                    session.setAttribute(keyView, o);
                }

                //判断是否重定向
                String viewUrl = modelAndView.getView();
                if(modelAndView.isRedirect()){
                    //重定向
                    resp.sendRedirect(viewUrl);
                }else{
                    //派发
                    req.getRequestDispatcher(viewUrl).forward(req, resp);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
