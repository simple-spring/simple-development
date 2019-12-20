package com.chexin.simple.development.core.init;

import com.chexin.simple.development.core.properties.PropertyConfigurer;
import com.chexin.simple.development.core.utils.ClassLoadUtil;
import com.chexin.simple.development.core.utils.JedisPoolUtils;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 12:48
 * @Description //TODO
 **/
public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[0];
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        try {
            // 读取项目配置文件
            PropertyConfigurer.loadProperties("application.properties");

            // 修改RootConfig扫描包的路径
            List<String> packageNames = new ArrayList<>();
            packageNames.add("com.chexin.simple.development.core.handler");
            packageNames.add("com.chexin.simple.development.core.jdbc");
            packageNames.add(PropertyConfigurer.getProperty("service.package.name"));
            packageNames.add(PropertyConfigurer.getProperty("dao.package.name"));
            Class rootConfig = ClassLoadUtil.javassistCompile("com.chexin.simple.development.core.init.RootConfig", "org.springframework.context.annotation.ComponentScan", packageNames, "basePackages");

            // 配置字符集过滤器
            FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding", CharacterEncodingFilter.class);
            characterEncoding.setInitParameter("forceEncoding", "true");
            characterEncoding.setInitParameter("encoding", "UTF-8");
            characterEncoding.addMappingForUrlPatterns(null, true, "/*");
            servletContext.addListener(RequestContextListener.class);

            // 创建Spring的root配置环境
            AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
            rootContext.register(rootConfig);

            // 将Spring的配置添加为listener
            servletContext.addListener(new ContextLoaderListener(rootContext));

            // 创建SpringMVC的分发器
            AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
            dispatcherContext.register(WebConfig.class);

            // 注册请求分发器
            ServletRegistration.Dynamic dispatcher =
                    servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
            dispatcher.setLoadOnStartup(1);
            dispatcher.addMapping("/");

            // 启动标志
            String isStart = PropertyConfigurer.getProperty("redis.enable");
            if (!StringUtils.isEmpty(isStart) && Boolean.getBoolean(isStart)) {
                System.out.println("redis启动....");
                JedisPoolUtils.init(PropertyConfigurer.getProperty("redisHost"), PropertyConfigurer.getProperty("redisPort"),
                        PropertyConfigurer.getProperty("redisPwd"),
                        Long.valueOf(PropertyConfigurer.getProperty("maxTotal")).intValue(),
                        Long.valueOf(PropertyConfigurer.getProperty("maxWaitMillis")),
                        Long.valueOf(PropertyConfigurer.getProperty("maxIdle")).intValue(),
                        Long.valueOf(PropertyConfigurer.getProperty("timeout")).intValue());
                System.out.println("redis启动成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("init fail", e);
        }
    }
}