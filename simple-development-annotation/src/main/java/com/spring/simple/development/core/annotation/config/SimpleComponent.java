package com.spring.simple.development.core.annotation.config;

import java.lang.annotation.*;

/**
 * 组件父注解
 *
 * @author liko wang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SimpleComponent {
}
