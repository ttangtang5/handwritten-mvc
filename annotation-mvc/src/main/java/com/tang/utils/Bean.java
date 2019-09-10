package com.tang.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description
 * @Author RLY
 * @Date 2018/12/3 16:44
 * @Version 1.0
 **/
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
public @interface Bean {

    String name();

    boolean singleton();
}
