package com.spring.simple.development.core.handler.event;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;

/**
 * 消息监听者
 *
 * @author liko wang
 */
public interface SimpleApplicationListener {
    /**
     * 收到通知
     *
     * @param servletContext
     * @param rootContext
     */
    void onApplicationEvent(ServletContext servletContext, AnnotationConfigWebApplicationContext rootContext);
}
