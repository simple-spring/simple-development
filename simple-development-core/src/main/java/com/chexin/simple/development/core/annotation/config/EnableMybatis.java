package com.chexin.simple.development.core.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不需要登录的地址
 *
 * @author liko wang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SimpleConfig
public @interface EnableMybatis {

    /**
     * mapper路径
     *
     * @return
     */
    String mapperPath() default "";

    /**
     * model路径
     *
     * @return
     */
    String modelPath() default "";

    /**
     * xml路径
     *
     * @return
     */
    String mapperXmlPath() default "";

    /**
     * 事务表达式
     *
     * @return
     */
    String expression() default "";
}
