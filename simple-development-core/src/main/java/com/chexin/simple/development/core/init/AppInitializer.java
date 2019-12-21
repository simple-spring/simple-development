package com.chexin.simple.development.core.init;

import com.chexin.simple.development.support.properties.PropertyConfigurer;
import com.chexin.simple.development.support.utils.ClassLoadUtil;
import com.chexin.simple.development.support.utils.JedisPoolUtils;
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
 * @Description 服务初始化
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
            System.out.println("初始化项目...");
            System.out.println("开始加载配置文件");
            // 读取项目配置文件
            PropertyConfigurer.loadApplicationProperties("application.properties");
            PropertyConfigurer.loadBaseProperties("/META-INF/simple-development");
            System.out.println("配置文件加载完成");

            System.out.println("spring 扫描包配置中...");

            // 修改RootConfig扫描包的路径
            List<String> packageNames = new ArrayList<>();
            packageNames.add(PropertyConfigurer.getBaseProperty("service.package.name"));
            packageNames.add(PropertyConfigurer.getBaseProperty("dao.package.name"));
            Class rootConfig = ClassLoadUtil.javassistCompile("com.chexin.simple.development.core.init.RootConfig", "org.springframework.context.annotation.ComponentScan", packageNames, "basePackages");

            // 修改WebConfig扫描包的路径
            List<String> mvcPackageNames = new ArrayList<>();
            mvcPackageNames.add("com.chexin.simple.development.core.mvc.controller");
            mvcPackageNames.add(PropertyConfigurer.getBaseProperty("controller.package.name"));
            Class webConfig = ClassLoadUtil.javassistCompile("com.chexin.simple.development.core.init.WebConfig", "org.springframework.context.annotation.ComponentScan", mvcPackageNames, "basePackages");
            System.out.println("spring 扫描包配置完成");


            System.out.println("spring 服务启动");

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

            System.out.println("spring容器启动成功");


            // 创建SpringMVC的分发器
            AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
            dispatcherContext.register(webConfig);

            // 注册请求分发器
            ServletRegistration.Dynamic dispatcher =
                    servletContext.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
            dispatcher.setLoadOnStartup(1);
            dispatcher.addMapping("/");
            System.out.println("spring mvc容器启动成功");


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