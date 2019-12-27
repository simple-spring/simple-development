package com.spring.simple.development.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注册api service服务
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsApiService {
    /**
     * 服务名
     *
     * @return
     */
    String value() default "";

    /**
     * 不需要登录的服务
     *
     * @return
     */
    boolean isLogin() default false;
}
