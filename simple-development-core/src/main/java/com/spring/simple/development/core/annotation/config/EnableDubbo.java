package com.spring.simple.development.core.annotation.config;

import java.lang.annotation.*;

/**
 * 开启dubbo
 *
 * @author liko wang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SimpleConfig
public @interface EnableDubbo {

    /**
     * dubbo扫描包路径
     *
     * @return
     */
    String dubboPackage() default "";
}
