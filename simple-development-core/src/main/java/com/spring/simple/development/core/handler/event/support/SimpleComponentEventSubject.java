package com.spring.simple.development.core.handler.event.support;

import com.spring.simple.development.core.handler.event.SimpleApplicationEventSubject;
import com.spring.simple.development.core.handler.event.SimpleApplicationListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 主题
 *
 * @author liko wang
 */
public class SimpleComponentEventSubject implements SimpleApplicationEventSubject<SimpleApplicationListener> {

    private ServletContext servletContext;
    private AnnotationConfigWebApplicationContext rootContext;

    public SimpleComponentEventSubject(ServletContext servletContext, AnnotationConfigWebApplicationContext rootContext) {
        this.servletContext = servletContext;
        this.rootContext = rootContext;
    }

    /**
     * 线程池
     */
    private static ExecutorService executorService = new ThreadPoolExecutor(2, 50, 1000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    /**
     * 存放订阅者
     */
    private List<SimpleApplicationListener> observers = new ArrayList<>();


    @Override
    public void addObserver(SimpleApplicationListener simpleComponentEvent) {
        observers.add(simpleComponentEvent);
    }

    /**
     * 消息异步处理
     */
    @Override
    public void notifyObserver() {
        for (SimpleApplicationListener simpleApplicationListener : observers) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    simpleApplicationListener.onApplicationEvent(servletContext, rootContext);
                }
            });
        }
        executorService.shutdown();
    }
}
