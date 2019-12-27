package com.spring.simple.development.core.init;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.SimpleConfig;
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
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 12:48
 * @Description 服务初始化
 **/
public class AppInitializer implements WebApplicationInitializer {
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
        try {
            System.out.println("spring simple start");
            System.out.println("spring simple config init ...");
            // 读取项目配置文件
            PropertyConfigurer.loadApplicationProperties("application.properties");
            System.out.println("spring simple config end");

            // 获取main类的包路径
            String basePackageName = System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME);
            if (StringUtils.isEmpty(basePackageName)) {
                throw new RuntimeException("base package is empty");
            }
            // 获取所有的组件注解实现
            Reflections sipReflections = new Reflections(SimpleSpiConfig.class);
            Set<Class<?>> classes = sipReflections.getTypesAnnotatedWith(Spi.class);
            if (CollectionUtils.isEmpty(classes)) {
                throw new RuntimeException("SimpleConfig is empty");
            }

            // 获取所有的组件注解
            Reflections configReflections = new Reflections("com.spring.simple.development.core.annotation.config");
            Set<Class<?>> simpleConfigList = configReflections.getTypesAnnotatedWith(SimpleConfig.class);
            if (CollectionUtils.isEmpty(simpleConfigList) == false) {
                for (Class simpleClass : simpleConfigList) {
                    for (Annotation annotation : annotationSet) {
                        // 选择需要加载的组件
                        if (simpleClass.getName().equals(annotation.annotationType().getName())) {
                            annotationMap.put(simpleClass.getSimpleName(), annotation);
                        }
                    }
                }
            }
            List<Object> configClassList = new ArrayList<>();

            for (Class configClass : classes) {
                Spi spi = (Spi) configClass.getAnnotation(Spi.class);
                // 获取组件名
                String configName = spi.configName();
                if (annotationMap.containsKey(configName)) {
                    Object configObject = configClass.newInstance();
                    Method method = configClass.getDeclaredMethod(SystemProperties.CONFIG_METHOD_NAME,annotationMap.get(configName).annotationType());
                    Object resultClass = method.invoke(configObject, annotationMap.get(configName));
                    configClassList.add(resultClass);
                }
            }
            if (CollectionUtils.isEmpty(configClassList)) {
                throw new RuntimeException("spring SimpleConfig is empty");
            }

            // 配置字符集过滤器
            FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding", CharacterEncodingFilter.class);
            characterEncoding.setInitParameter("forceEncoding", "true");
            characterEncoding.setInitParameter("encoding", "UTF-8");
            characterEncoding.addMappingForUrlPatterns(null, true, "/*");
            servletContext.addListener(RequestContextListener.class);


            // 创建Spring的root配置环境
            AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
            rootContext.setBeanName("spring simple "+System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_NAME));
            Class[] configClass = new Class[configClassList.size()];
            rootContext.register(configClassList.toArray(configClass));
            // 将Spring的配置添加为listener
            servletContext.addListener(new ContextLoaderListener(rootContext));
            // TODO: 2019/12/26 0026 mvc后续再做封装
            // 注册请求分发器
            ServletRegistration.Dynamic dispatcher =
                    servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
            dispatcher.setLoadOnStartup(1);
            dispatcher.addMapping(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MVC_CONFIG_URL_PATH));

            System.out.println("spring simple initialized successful");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("spring simple initialized fail", e);
        }
    }
}