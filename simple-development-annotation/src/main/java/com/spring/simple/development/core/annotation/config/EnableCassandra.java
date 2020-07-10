package com.spring.simple.development.core.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * cassandra组件
 *
 * @author liko wang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SimpleComponent
public @interface EnableCassandra {

    /**
     * cassandra组件扫描包路径
     *
     * @return
     */
    String cassandraPackage() default "";

}
