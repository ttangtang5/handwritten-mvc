package com.tang.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @Description
 * @Author RLY
 * @Date 2018/11/28 10:45
 * @Version 1.0
 **/
public class ProperitesUitls {

    public static void loadFile(File file){
        try {
            URL resource = ProperitesUitls.class.getClassLoader().getResource("config.properties");
            file = new File(resource.toURI());
            InputStream is = new FileInputStream(file);
            Properties properties = new Properties();
            properties.load(is);
            String name = properties.getProperty("name");
            System.out.println(name);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static void main(String[] args) {
        File file = null;
        ProperitesUitls.loadFile(file);
    }
}
