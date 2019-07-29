package com.scy.component.mylibrary.annotation;
//元注解： 注解上的注解

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sunchaoyang
 */


@Retention(RetentionPolicy.RUNTIME) //给反射用
@Target(ElementType.TYPE)
public @interface ServiceId {
    String value();
}
