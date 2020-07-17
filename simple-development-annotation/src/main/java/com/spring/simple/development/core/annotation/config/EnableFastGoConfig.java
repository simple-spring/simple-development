package com.spring.simple.development.core.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author luke
 * @desc: FastGoConfig组件
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SimpleComponent
public @interface EnableFastGoConfig {
    /**
     * git分支名
     *
     * @return
     */
    String branch();

    /**
     * 项目Code
     *
     * @return
     */
    String projectCode();

    /**
     * 项目地址
     *
     * @return
     */
    String fastGoServer();

}
