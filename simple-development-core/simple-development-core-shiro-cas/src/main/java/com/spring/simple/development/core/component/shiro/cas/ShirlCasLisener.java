package com.spring.simple.development.core.component.shiro.cas;

import com.spring.simple.development.core.init.AppInitializer;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import java.util.EnumSet;

@WebListener
public class ShirlCasLisener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        AppInitializer.servletContext.addListener(SingleSignOutHttpSessionListener.class);

        ServletContext context = servletContextEvent.getServletContext();

        // shiro 过滤
        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
        delegatingFilterProxy.setTargetFilterLifecycle(true);
        FilterRegistration.Dynamic shiroFilter = context.addFilter("shiroFilter", delegatingFilterProxy);
        //配置mapping
        //REQUEST：普通模式，来自客户端的请求,ASYNC:来自AsyncContext的异步请求
        // false则是在web.xml定义的filter后代码设置的
        shiroFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC), false, "/*");

        // cas 过滤
        FilterRegistration.Dynamic casFilter = context.addFilter("CAS Single Sign Out Filter", SingleSignOutFilter.class);
        //配置mapping
        casFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ASYNC), false, "/shiro-cas");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
