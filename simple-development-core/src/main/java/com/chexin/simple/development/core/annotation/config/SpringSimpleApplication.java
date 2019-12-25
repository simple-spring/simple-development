package com.chexin.simple.development.core.annotation.config;

import com.chexin.simple.development.core.config.SimpeConfigFilters;

import java.lang.annotation.*;

/**
 * simple程序入口
 *
 * @author liko wang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SimpleConfig
public @interface SpringSimpleApplication {

    /**
     * 应用名
     *
     * @return
     */
    String applicationName() default "";

    /**
     * 应用包路径
     *
     * @return
     */
    String appPackagePathName() default "";
}
