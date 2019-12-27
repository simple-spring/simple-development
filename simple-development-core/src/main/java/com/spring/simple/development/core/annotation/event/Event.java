package com.spring.simple.development.core.annotation.event;

import java.lang.annotation.*;

/**
 * 事件
 *
 * @author liko wang
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Event {
}
