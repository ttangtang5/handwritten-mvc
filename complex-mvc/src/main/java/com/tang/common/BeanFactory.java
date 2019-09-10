package com.tang.common;

import com.tang.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {

    private static BeanFactory beanFactory = null;

    private static Map<String, BeanConfig> beanConfigs = new HashMap<String, BeanConfig>();

    //The objects only storage the singleton object
    private static Map<String, Object> objects = new HashMap<String, Object>();

    private BeanFactory() {
    }

    public synchronized static BeanFactory getInstance() {
        if (beanFactory == null) {
            beanFactory = new BeanFactory();
            beanFactory.init();
        }
        return beanFactory;
    }

    private void init() {
        InputStream inputStream = null;
        try {
            inputStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element element = document.getDocumentElement();
            NodeList beanNode = element.getElementsByTagName("bean");

            if (beanNode != null) {
                int beanNodeLength = beanNode.getLength();
                for (int i = 0 ;i < beanNodeLength; i++) {
                    Element beanElement = (Element) beanNode.item(i);
                    BeanConfig beanConfig = new BeanConfig();
                    String id = beanElement.getAttribute("id");
                    beanConfig.setId(id);
                    String clsName = beanElement.getAttribute("class");
                    beanConfig.setClsName(clsName);
                    String scope = beanElement.getAttribute("scope");
                    beanConfig.setScope(scope);
                    beanConfigs.put(id, beanConfig);
                    NodeList propertyNode = beanElement.getElementsByTagName("property");
                    if (propertyNode != null) {
                        int propertyNodeLength = propertyNode.getLength();
                        for (int j = 0; j < propertyNodeLength; j++) {
                            Element propertyElement = (Element) propertyNode.item(j);
                            BeanProperty beanProperty = new BeanProperty();
                            String name = propertyElement.getAttribute("name");
                            beanProperty.setName(name);
                            String value = propertyElement.getAttribute("value");
                            beanProperty.setValue(value);
                            String ref = propertyElement.getAttribute("ref");
                            beanProperty.setRef(ref);
                            beanConfig.getBeanPropertys().add(beanProperty);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String id) {
        if (beanConfigs.containsKey(id)) {
            BeanConfig beanConfig = beanConfigs.get(id);
            String scope = beanConfig.getScope();

            //Default singleton
            if (StringUtil.checkString(scope)) {
                beanConfig.setScope("singleton");
            }
            //First judge the object whether set singleton
            if ("singleton".equalsIgnoreCase(scope)) {
                //True ==> Find the id in key of objects
                if (objects.containsKey(id)) {
                    //True ==> return object
                    return objects.get(id);
                }
            }

            try {
                Class<?> cls = Class.forName(beanConfig.getClsName());
                Object obj = cls.newInstance();
                if ("singleton".equalsIgnoreCase(scope)) {
                    objects.put(id, obj);
                }

                //The beanPropertys must is not null
                ArrayList<BeanProperty> beanPropertys = (ArrayList<BeanProperty>) beanConfig.getBeanPropertys();

                if (!beanPropertys.isEmpty()) {
                    for (BeanProperty beanProperty : beanPropertys) {
                        String name = beanProperty.getName(); // --> userDao
                        String firstChar = name.substring(0, 1);
                        String leaveChar = name.substring(1);
                        String methodName = "set" + firstChar.toUpperCase() + leaveChar; // --> setUserDao

                        //Get all of method in the "cls"
                        Method[] methods = cls.getMethods();

                        Method method = null;
                        for (Method methodNameCls : methods) {
                            //It exits the same method
                            if (methodName.equals(methodNameCls.getName())) {
                                method = methodNameCls;
                                break;
                            }
                        }

                        //TODO The method maybe is null
                        String ref = beanProperty.getRef();
                        String value = beanProperty.getValue();
                        Class<?>[] parameters = method.getParameterTypes();
                        if (!StringUtil.checkString(ref)) {
                           //Recursion getBean() method for create object of "ref"
                           Object refObject =  this.getBean(ref);
                           //obj is a Object but refObject only a parameter  obj invoke setParameter method.
                           method.invoke(obj, refObject);
                        } else if (!StringUtil.checkString(value)) {
                            for (int j = 0; j < parameters.length; j++ ) {
                                if (parameters[j] == String.class) {
                                    method.invoke(obj, value);
                                }
                                if (parameters[j] == int.class) {
                                    method.invoke(obj, Integer.parseInt(value));
                                }
                                if (parameters[j] == double.class) {
                                    method.invoke(obj, Double.parseDouble(value));
                                }
                            }
                        }

                        //If the object under the com.tang.service.impl package
                        if (obj.getClass().getPackage().getName().equals("com.tang.service.impl")) {
                            ConnectionDynamicProxy connectionDynamicProxy = new ConnectionDynamicProxy();
                            connectionDynamicProxy.setObj(obj);//设置属性  将要被代理的对象set进去

                            Object proxyObject = Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                                                                        obj.getClass().getInterfaces(),
                                                                        connectionDynamicProxy);
                            return proxyObject;
                        }

                    }
                }
                return obj;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
