package com.spring.simple.development.core.annotation.base;

import javax.validation.constraints.Null;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注册service 方法名称自定义
 * @author Administrator
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IsApiMethodService {
    /**
     * 修改方法名
     * @return
     */
    String value() default "";

    /**
     * 方法参数key
     *
     * @return
     */
    String[] key();

    /**
     * 方法参数类型
     *
     * @return
     */
    Class<?>[] methodClass();
}
