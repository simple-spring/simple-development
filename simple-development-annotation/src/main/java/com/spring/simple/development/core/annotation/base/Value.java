package com.spring.simple.development.core.annotation.base;

import java.lang.annotation.*;

/**
 * @author liko.wang
 * @Date 2019/12/20/020 12:39
 * @return
 * @Description 自定义属性注解
 **/
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Value {
    String value();
}
