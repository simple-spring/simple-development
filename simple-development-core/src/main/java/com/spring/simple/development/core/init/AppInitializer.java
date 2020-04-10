package com.spring.simple.development.core.init;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.component.ComponentContainer;
import com.spring.simple.development.core.handler.event.SimpleApplicationEventSubject;
import com.spring.simple.development.core.handler.event.support.SimpleComponentEventSubject;
import com.spring.simple.development.core.handler.listener.SimpleComponentListener;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.reflections.Reflections;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 12:48
 * @Description 服务初始化
 **/
public class AppInitializer implements WebApplicationInitializer {
    public static ServletContext servletContext;
    public static AnnotationConfigWebApplicationContext rootContext;
    /**
     * 组件寄存器
     */
    public static Set<Annotation> annotationSet = new HashSet<>();

    /**
     * 选配的组件寄存器
     */
    public static Map<String, Annotation> annotationMap = new HashMap<>();

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AppInitializer.servletContext = servletContext;
        try {
            System.out.println("spring simple start");
            System.out.println("spring simple config init ...");
            // 读取项目配置文件
            PropertyConfigurer.loadApplicationProperties("application.properties");
            System.out.println("spring simple config end");

            // 获取main类的包路径
            String basePackageName = System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME);
            if (StringUtils.isEmpty(basePackageName)) {
                System.out.println("spring simple base package is empty");
                return;
            }
            if (CollectionUtils.isEmpty(annotationSet)) {
                System.out.println("not enabled spring simple component");
                return;
            }
            // 组件容器初始化
            ComponentContainer.initComponentContainer();

            for (Annotation annotation : annotationSet) {
                // 选择加载的组件
                if (ComponentContainer.Components.containsKey(annotation.annotationType().getName())) {
                    System.out.println(ComponentContainer.Components.get(annotation.annotationType().getName()).getSimpleName() + "init");
                    annotationMap.put(ComponentContainer.Components.get(annotation.annotationType().getName()).getSimpleName(), annotation);
                }
            }
            // 是否有启动的组件
            if (CollectionUtils.isEmpty(annotationMap)) {
                System.out.println("not enabled spring simple component");
                return;
            }

            List<Object> configClassList = new ArrayList<>();

            // 获取所有的组件注解实现
            System.out.println("Reflections SimpleSpi start");
            Reflections sipReflections = new Reflections(SimpleSpiConfig.class);
            Set<Class<?>> classes = sipReflections.getTypesAnnotatedWith(Spi.class);
            System.out.println("Reflections SimpleSpi end");
            if (CollectionUtils.isEmpty(classes)) {
                throw new RuntimeException("spring simple component is empty");
            }
            // 通过spi找到组件
            for (Class configClass : classes) {
                Spi spi = (Spi) configClass.getAnnotation(Spi.class);
                // 获取组件名
                String configName = spi.configName();
                if (annotationMap.containsKey(configName)) {
                    Object configObject = configClass.newInstance();
                    Method method = configClass.getDeclaredMethod(SystemProperties.CONFIG_METHOD_NAME, annotationMap.get(configName).annotationType());
                    Object resultClass = method.invoke(configObject, annotationMap.get(configName));
                    if (resultClass == null) {
                        continue;
                    }
                    configClassList.add(resultClass);
                }
            }
            if (CollectionUtils.isEmpty(configClassList)) {
                System.out.println("not enabled spring simple");
                return;
            }

            // 配置字符集过滤器
            FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding", CharacterEncodingFilter.class);
            characterEncoding.setInitParameter("forceEncoding", "true");
            characterEncoding.setInitParameter("encoding", "UTF-8");
            characterEncoding.addMappingForUrlPatterns(null, true, "/*");
            servletContext.addListener(RequestContextListener.class);

            // 启动shiro
            boolean isEnableBoolean = Boolean.parseBoolean(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_ISOPEN));
            if(isEnableBoolean){
                AppInitializer.servletContext.addListener("org.jasig.cas.client.session.SingleSignOutHttpSessionListener");

                FilterRegistration.Dynamic filterRegistration = AppInitializer.servletContext.addFilter("shiroFilter", "org.springframework.web.filter.DelegatingFilterProxy");
                filterRegistration.setInitParameter("targetFilterLifecycle", "true");
                //配置mapping
                filterRegistration.addMappingForUrlPatterns(null, false, "/**");

                //cas filter
                javax.servlet.FilterRegistration.Dynamic filter = AppInitializer.servletContext.addFilter("CAS Single Sign Out Filter", "org.jasig.cas.client.session.SingleSignOutFilter");
                //配置mapping
                filter.addMappingForUrlPatterns(null, false, "/shiro-cas");
            }

            // 创建Spring的root配置环境
            rootContext = new AnnotationConfigWebApplicationContext();
            rootContext.setBeanName("spring simple " + System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_NAME));
            // 注册并启动
            Class[] configClass = new Class[configClassList.size()];
            rootContext.register(configClassList.toArray(configClass));
            // 将Spring的配置添加为listener
            servletContext.addListener(new ContextLoaderListener(rootContext));

            // 组件扫描事件
            scanEven();
            System.out.println("spring simple initialized successful");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("spring simple initialized fail", e);
        }
    }

    /**
     * scan event
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void scanEven() throws IllegalAccessException, InstantiationException {
        // 主题
        System.out.println("reflections scanEven start");
        SimpleApplicationEventSubject simpleApplicationEventSubject = new SimpleComponentEventSubject(AppInitializer.servletContext, AppInitializer.rootContext);
        Reflections reflections = new Reflections("com.spring.simple.development.core");
        Set<Class<? extends SimpleComponentListener>> subTypes = reflections.getSubTypesOf(SimpleComponentListener.class);
        System.out.println("reflections scanEven end");

        if (!CollectionUtils.isEmpty(subTypes)) {
            for (Class aclass : subTypes) {
                simpleApplicationEventSubject.addObserver(aclass.newInstance());
            }
        }
        simpleApplicationEventSubject.notifyObserver();
    }

}