package com.spring.simple.development.core.handler.listener;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;

/**
 * 组件消息监听者
 *
 * @author liko wang
 */
public interface SimpleComponentListener extends SimpleApplicationListener {


    /**
     * 收到通知
     *
     * @param servletContext
     * @param rootContext
     */
    @Override
    void onApplicationEvent(ServletContext servletContext, AnnotationConfigWebApplicationContext rootContext);
}
