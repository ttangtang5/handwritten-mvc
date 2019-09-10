package com.tang.util;

import java.io.*;
import java.util.Properties;

/**
 * @FileName: Test
 * @Author: 16
 * @Date: 2018/9/19 10:59
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        System.out.println(Test.class.getResource("/app.properties").toString());
        System.out.println(Test.class.getResource("/app.properties").getRef());
        System.out.println(Test.class.getResource("/app.properties").getFile());
        System.out.println(Test.class.getResource("/app.properties").getPath());
        File file = new File(Test.class.getResource("/app.properties").getPath());
        System.out.println(file.exists());
        InputStream inputStream = Test.class.getClassLoader().getResourceAsStream("app.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            System.out.println(properties.getProperty("root"));
            inputStream.close();
            String filePath = Test.class.getResource("/app.properties").toString();
            System.out.println(filePath);
            System.out.println(Test.class.getClassLoader().getResource("app.properties").toString().substring(6));
            //File file = new File("E:/Develop/springdemo/target/classes/app.properties");
            OutputStream outputStream = new FileOutputStream(file);
            properties.setProperty("root", "root12");
            properties.store(outputStream,"update");
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
