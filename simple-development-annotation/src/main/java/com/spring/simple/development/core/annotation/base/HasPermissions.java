package com.spring.simple.development.core.annotation.base;

import com.spring.simple.development.core.commonenum.Logical;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface HasPermissions {

    /**
     * 权限字符串集合
     *
     * @return
     * @author liko.wang
     * @date 2017年11月23日下午6:06:24
     */
    String[] value() default {};

    /**
     * 权限逻辑关系
     *
     * @return
     * @author liko.wang
     * @date 2017年11月23日下午6:06:30
     */
    Logical logical() default Logical.And;
}
