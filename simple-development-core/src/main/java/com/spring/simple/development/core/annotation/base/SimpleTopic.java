package com.spring.simple.development.core.annotation.base;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @desc: Kafka Topic声明
 * @auth: hjs
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SimpleTopic {
    /**
     * @param
     * @return java.lang.String
     * @author liko.wang
     * @Date 2020/4/7/007 8:57
     * @Description 主题名
     **/
    String value();

    /**
     * @param
     * @return java.lang.String
     * @author liko.wang
     * @Date 2020/4/7/007 8:57
     * @Description 组名
     **/
    String groupName();
}
