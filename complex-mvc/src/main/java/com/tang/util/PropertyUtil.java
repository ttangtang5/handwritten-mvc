package com.tang.util;

import com.tang.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyUtil {

    private static Properties properties = null;

    static {
        InputStream is = null;
        is = PropertyUtil.class.getClassLoader().getResourceAsStream(Constants.PROPERTIES);
        try {
            properties = new Properties();
            properties.load(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getProperties(String key) {
        return properties.getProperty(key);
    }
}
