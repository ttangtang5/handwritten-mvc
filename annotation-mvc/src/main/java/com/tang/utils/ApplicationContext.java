package com.tang.utils;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 通过反射生成bean   bean singleton默认true
 * @Author RLY
 * @Date 2018/11/28 10:37
 * @Version 1.0
 **/
public class ApplicationContext {

    public static ApplicationContext applicationContext = null;

    public static Map<String, BeanConfig> beanMap = new HashMap<String, BeanConfig>();

    public static Map<String, Object> singletonMap = new HashMap<String, Object>();

    public ApplicationContext() {
    }

    public static ApplicationContext getBeanFactory(){
        if(applicationContext == null){
            applicationContext = new ApplicationContext();
            applicationContext.initContext();
        }
        return applicationContext;
    }

    private void initContext(){
        String key = null;
        String cls = null;
        boolean scope = true;
        BeanConfig beanConfig = null;

        try {
            InputStream is = ApplicationContext.class.getResourceAsStream("/bean.xml");

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(is);

            NodeList beans = document.getElementsByTagName("beanConfig");
            int len = beans.getLength();
            for(int i = 0; i < len; i++){
                beanConfig = new BeanConfig();
                Node item = beans.item(i);
                NamedNodeMap attributes = item.getAttributes();
                key = attributes.getNamedItem("id").getNodeValue();
                cls = attributes.getNamedItem("class").getNodeValue();
                String scopeStr = attributes.getNamedItem("singleton").getNodeValue();
                scope = (scopeStr != null)? Boolean.getBoolean(scopeStr) : false;

                beanConfig.setName(key);
                beanConfig.setCls(cls);
                beanConfig.setSingleton(scope);
                beanMap.put(key, beanConfig);

                //处理单例
                if(scope){
                    Object obj = Class.forName(cls).newInstance();
                    singletonMap.put(key, obj);
                }
            }
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public Object getBean(String name){
        Object obj = null;
        if(name != null && !"".equals(name)){
            if(beanMap.containsKey(name)){
                BeanConfig beanConfig = beanMap.get(name);
                if(beanConfig != null){
                    if(beanConfig.isSingleton()){
                        //单例直接从容器取出
                        if(singletonMap.containsKey(name)){
                            obj = singletonMap.get(name);
                        }
                    } else{
                        //非单例 反射生成对象
                        String beanCls = beanConfig.getCls();
                        try {
                            obj = Class.forName(beanCls).newInstance();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return obj;
    }

}
