package com.spring.simple.development.core.handler.event;

/**
 * 事件发布者
 *
 * @author liko wang
 */
public interface SimpleApplicationEventSubject <T>{

    /**
     * 添加订阅者
     * @param t
     */
    void addObserver(T t);

    /**
     * 通知
     */
    void notifyObserver();
}
