package com.tang.utils;

import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;

/**
 * @Description
 * @Author RLY
 * @Date 2018/12/4 14:34
 * @Version 1.0
 **/
public class FileUitls {

    /**
     * 获取对应吗目录下所有的class
     * @param parentFile
     */
    public static List<File> getClassFile(File parentFile){
        List<File> tempFile = Lists.newArrayList();
        File[] listFiles = parentFile.listFiles();

        for(File file : listFiles){
            if(file.isDirectory()){
                getClassFile(file);
            }else if(file.getName().endsWith(".class")){
                tempFile.add(file);
            }
        }

        return tempFile;
    }
}
