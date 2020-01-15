package com.spring.simple.development.core.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启Swagger
 *
 * @author ys
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SimpleComponent
public @interface EnableSwagger {
    /**
     * @param
     * @return java.lang.String
     * @author liko.wang
     * @Date 13:52
     * @Description 文档标题
     **/
    String title() default "";

    /**
     * @param
     * @return java.lang.String
     * @author liko.wang
     * @Date 13:52
     * @Description 文档描述
     **/
    String description() default "";

    /**
     * @param
     * @return java.lang.String
     * @author liko.wang
     * @Date 13:52
     * @Description 文档作者
     **/
    String contact() default "";

    /**
     * @param
     * @return java.lang.String
     * @author liko.wang
     * @Date 13:52
     * @Description 文档版本
     **/
    String version() default "";

    /**
     * @param
     * @return java.lang.String
     * @author liko.wang
     * @Date 13:52
     * @Description 项目地址
     **/
    String url() default "";
}
