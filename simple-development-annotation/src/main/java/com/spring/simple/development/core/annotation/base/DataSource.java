package com.spring.simple.development.core.annotation.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * desc:    自定义数据源注解(如果有事务，则无法动态切换数据源,应加在事务的上一层)
 *
 * @author liko wang
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    String value();
}