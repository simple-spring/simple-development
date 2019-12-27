package com.spring.simple.development.core.annotation.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启mvc
 *
 * @author liko wang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SimpleComponent
public @interface EnableWebMvc {

    /**
     * 项目根路径
     *
     * @return
     */
    String urlPath() default "/";

    /**
     * controller层包路径
     *
     * @return
     */
    String packagePath() default "";

    /**
     * 拦截器包路径
     * @return
     */
    String interceptorPackagePath() default "";
}
