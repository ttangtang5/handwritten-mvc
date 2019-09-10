package com.tang.common;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @FileName: BeanFactory
 * @Author: 16
 * @Date: 2018/8/29 11:24
 * @Description: bean工厂 bean容器 创建bean 此类为单例
 */
public class BeanFactory {

    public static BeanFactory beanFactory = null;

    /**
     * 存储读取配置文件的bean配置信息
     */
    private Map<String, BeanConfig> beanConfig = new HashMap<String, BeanConfig>();

    /**
     * 存储创建的单例对象
     */
    private Map<String, Object> singletonObject = new HashMap<String, Object>();

    public static BeanFactory getBeanFactory(){
        if(beanFactory == null){
            beanFactory = new BeanFactory();
            //创建BeanFactory后 初始化beanFactory 将配置文件的配置信息加载进容器
            beanFactory.init();
        }
        return beanFactory;
    }

    private void init(){
        InputStream is = null;

        try {
            is = beanFactory.getClass().getClassLoader().getResourceAsStream("bean.xml");

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(is);
            NodeList nodeList = document.getElementsByTagName("bean");
            //将配置文件信息加载进容器
            if(nodeList != null){
                for (int i = 0;i < nodeList.getLength(); i++){
                    Element element = (Element) nodeList.item(i);
                    String id = element.getAttribute("id");
                    String cls = element.getAttribute("class");
                    Boolean scope = "singleton".equalsIgnoreCase(element.getAttribute("scope"));

                    BeanConfig bean = new BeanConfig();
                    bean.setId(id);
                    bean.setCls(cls);
                    bean.setScope(scope);

                    //获取该bean的属性配置
                    NodeList propertyList = element.getElementsByTagName("property");
                    if(propertyList != null){
                        for (int j = 0;j < propertyList.getLength(); j++){
                            Element proElement = (Element) propertyList.item(j);
                            String name = proElement.getAttribute("name");
                            String ref = proElement.getAttribute("ref");
                            String value = proElement.getAttribute("value");

                            BeanProperty property = new BeanProperty();
                            property.setName(name);
                            property.setRef(ref);
                            property.setValue(value);

                            bean.getBeanProperty().put(name, property);
                        }
                    }

                    beanConfig.put(id, bean);
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

    public Object getBean(String clsName){
        //判断是否存在该对象
        if(beanConfig.containsKey(clsName)){
            //单例容器存在直接返回
            if(singletonObject.containsKey(clsName)){
                return singletonObject.get(clsName);
            }

            BeanConfig bean  = beanConfig.get(clsName);
            if(bean != null){
                String cls = bean.getCls();
                Boolean singleton = bean.getScope();
                
                try {
                    //TODO 暂未处理bean对象中属性未初始化
                    Class<?> objClass = Class.forName(cls);
                    Object obj = objClass.newInstance();
                    if(singleton){
                        singletonObject.put(cls, obj);
                    }
                    return obj;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
