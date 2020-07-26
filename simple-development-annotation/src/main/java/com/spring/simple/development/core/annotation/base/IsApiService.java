package com.spring.simple.development.core.annotation.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注册api service服务
 * @author liko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IsApiService {
    /**
     * 服务名
     * @return
     */
    String serviceName() default "";
    /**
     * 方法名
     *
     * @return
     */
    String methodName() default "";

    /**
     * 是否需要登录的服务
     *
     * @return
     */
    boolean isLogin() default true;
}
