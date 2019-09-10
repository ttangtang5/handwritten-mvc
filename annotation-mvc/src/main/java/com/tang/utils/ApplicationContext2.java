package com.tang.utils;

import com.google.common.collect.Lists;
import com.tang.servlet.DispatcherServlet2;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 通过反射生成bean   bean singleton默认true
 * @Author RLY
 * @Date 2018/11/28 10:37
 * @Version 1.0
 **/
public class ApplicationContext2 {

    public static ApplicationContext2 applicationContext = null;

    public static Map<String, BeanConfig> beanMap = new HashMap<String, BeanConfig>();

    public static Map<String, Object> singletonMap = new HashMap<String, Object>();

    public ApplicationContext2() {
    }

    public static ApplicationContext2 getBeanFactory(){
        if(applicationContext == null){
            applicationContext = new ApplicationContext2();
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
            //处理xml配置的bean
            InputStream is = ApplicationContext2.class.getResourceAsStream("/bean.xml");

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

            //处理注解的bean
            //遍历扫描的包下面的类
            List<File> classList = Lists.newArrayList();
            URL resource = this.getClass().getResource("/com/tang");
            File file = new File(resource.getFile());
            File[] listFiles = file.listFiles();
            for (File tempFile : listFiles){
                List<File> tempList = FileUitls.getClassFile(tempFile);
                classList.addAll(tempList);
            }
            //反射获取类熟悉 判断是否有注解
            for(File tempFile : classList){
                String[] urlName = tempFile.getPath().split("classes");
                if(urlName.length > 1){
                    String fileUrl = tempFile.getPath().split("classes")[1];
                    int tempLen = fileUrl.length();
                    fileUrl = StringUtils.substring(fileUrl, 1, tempLen - 6);
                    String className = fileUrl.replaceAll("\\\\", ".");
                    System.out.println(className);
                    Class<?> classes = Class.forName(className);
                    Annotation[] annotations = classes.getAnnotations();
                    System.out.println(annotations.length);
                    //Object o = classes.newInstance();
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
