package com.tang.utils;

import com.google.common.collect.Lists;
import org.springframework.asm.ClassReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @Description
 * @Author RLY
 * @Date 2018/12/3 17:26
 * @Version 1.0
 **/
public class Test {

    public static void main(String[] args) throws ClassNotFoundException, IOException {
        //ApplicationContext2 applicationContext2 = ApplicationContext2.getBeanFactory();
        //Class<?> forName = Class.forName("com.tang.servlet.DispatcherServlet");
        //System.out.println(111);
        BufferedInputStream is = (BufferedInputStream) Test.class.getResourceAsStream("Bean.class");
        ClassLoader classLoader = Test.class.getClassLoader();
        ClassReader classReader = null;
        try {
            classReader = new ClassReader(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor(classLoader);
        classReader.accept(visitor, 2);
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(AnnotationBeanConfig.class);

    }

}
