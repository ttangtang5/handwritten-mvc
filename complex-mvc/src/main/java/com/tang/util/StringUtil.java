package com.tang.util;

public final class StringUtil {

    public static boolean checkString(String content) {
        return content == null || "".equals(content) ? true : false;
    }

    public static String replaceAll(String content, String resource, String aim) {
        if (content.contains(resource)) {
            content = content.replaceAll(resource, aim);
        }
        return content;
    }

    public static void main(String[] args) {
        String str = "<54234asf>";
        System.out.println(replaceAll(replaceAll(str, "<", "&lt"), ">", "&gt"));
    }

}
