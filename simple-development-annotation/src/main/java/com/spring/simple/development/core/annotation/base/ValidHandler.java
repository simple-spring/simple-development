package com.spring.simple.development.core.annotation.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liko
 * @Date 2019-10-02 13:40
 * @DESCRIPTION 自定义参数校验
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidHandler {
    /**
     * ReqBody参数key与value顺序对应
     *
     * @return
     */
    String[] key() default "";

    /**
     * ReqBody参数key与value顺序对应
     *
     * @return
     */
    Class<?>[] value();

    /**
     * 是否是分页
     *
     * @return
     */
    boolean isPage() default false;

    /**
     * 是否是isApiService
     *
     * @return
     */
    boolean isReqBody() default false;
}