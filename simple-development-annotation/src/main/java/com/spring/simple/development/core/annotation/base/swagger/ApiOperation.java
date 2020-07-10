package com.spring.simple.development.core.annotation.base.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义swagger @ApiOperation
 *
 * @author liko wang
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiOperation {
    /**
     * 接口title
     *
     * @return
     */
    String value() default "";

    /**
     * 接口描述
     */
    String notes() default "";
}
