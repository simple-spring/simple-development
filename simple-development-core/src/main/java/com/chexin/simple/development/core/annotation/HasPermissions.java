package com.chexin.simple.development.core.annotation;

import com.chexin.simple.development.core.commonenum.Logical;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解
 * 
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasPermissions {

    /**
     * 权限字符串集合
     * 
     * @return
     */
    String[] value() default { };

    /**
     * 逻辑关系
     */
    Logical LOGICAL() default Logical.Or;
}
