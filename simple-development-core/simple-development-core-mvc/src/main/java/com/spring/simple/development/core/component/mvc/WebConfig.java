package com.spring.simple.development.core.component.mvc;

import com.spring.simple.development.core.annotation.base.SimpleInterceptor;
import com.spring.simple.development.core.baseconfig.context.SimpleContentApplication;
import com.spring.simple.development.core.component.mvc.interceptor.ApiSupportInterceptor;
import com.spring.simple.development.core.component.shiro.cas.ShiroCasInterceptor;
import com.spring.simple.development.core.component.shiro.cas.ShiroLavaSupportInterceptor;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.reflections.Reflections;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Set;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 12:49
 * @Description //TODO
 **/
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    public WebConfig() {
        System.out.println("mvc init");
    }

    @Bean
    public BaseSupport baseSupport() {
        return new BaseSupport();
    }

    /**
     * @param
     * @return org.springframework.web.servlet.ViewResolver
     * @author liko.wang
     * @Date 2019/12/19/019 13:28
     * @Description 注册视图解析器
     **/
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
        resourceViewResolver.setPrefix("/WEB-INF/views/");
        resourceViewResolver.setSuffix(".jsp");
        resourceViewResolver.setExposeContextBeansAsAttributes(true);
        return resourceViewResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public ApiSupportInterceptor getApiSupportInterceptor() {
        return new ApiSupportInterceptor();
    }

    /**
     * @param registry
     * @return void
     * @author liko.wang
     * @Date 2019/12/19/019 13:27
     * @Description 注册拦截器
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) SimpleContentApplication.applicationContext.getAutowireCapableBeanFactory();
        // 默认拦截器
        String[] excludes = new String[5];
        excludes[0] = "/swagger-ui.html";
        excludes[1] = "/webjars/**";
        excludes[2] = "/swagger-resources";
        excludes[3] = "/swagger-resources/configuration/ui";
        excludes[4] = "/swagger-resources/configuration/security";

        // 启动shiro
        try {
            boolean isEnableShiroCASBoolean = Boolean.parseBoolean(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_ISOPEN));
            if (isEnableShiroCASBoolean) {

                // shiro-lava支持
                registry.addInterceptor(new ShiroLavaSupportInterceptor()).addPathPatterns("/**").excludePathPatterns(excludes);

                ShiroCasInterceptor shiroCasInterceptor = new ShiroCasInterceptor();
                // 添加mvc拦截器
                registry.addInterceptor(shiroCasInterceptor.getDefaultBizAuthenticationHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns(excludes);
                registry.addInterceptor(shiroCasInterceptor.getAuthenticationHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns(excludes);
                registry.addInterceptor((shiroCasInterceptor.getAuthorizationHandlerInterceptor())).addPathPatterns("/**").excludePathPatterns(excludes);
            }
        } catch (Exception e) {
            throw new RuntimeException("shiro cas interptor 加载失败");
        }

        registry.addInterceptor(getApiSupportInterceptor()).excludePathPatterns(excludes);
        try {
            System.out.println("reflections SimpleInterceptor start");
            Reflections reflections = new Reflections(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MVC_CONFIG_INTERCEPTOR_PATH));
            Set<Class<?>> classes = reflections.getTypesAnnotatedWith(SimpleInterceptor.class);
            System.out.println("reflections SimpleInterceptor end");
            if (CollectionUtils.isEmpty(classes)) {
                return;
            }
            for (Class clazz : classes) {
                // 解决自定义拦截器中无法注入bean
                //创建bean信息
                BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
                //动态注册bean
                defaultListableBeanFactory.registerBeanDefinition(clazz.getSimpleName(), beanDefinitionBuilder.getBeanDefinition());
                System.out.println("SimpleInterceptor class: " + clazz.getName());
                // 获取bean
                Object bean = SimpleContentApplication.applicationContext.getBean(clazz.getSimpleName());
                registry.addInterceptor((HandlerInterceptor) bean).excludePathPatterns(excludes);
            }

        } catch (Exception e) {
            throw new RuntimeException("mvc 拦截器注册失败", e);
        }
    }

    @Bean
    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        return new ServletRegistrationBean(dispatcherServlet, PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MVC_CONFIG_URL_PATH));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String isEnable = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_IS_ENABLE);
        boolean isEnableBoolean = Boolean.parseBoolean(isEnable);
        // 不启动
        if (!isEnableBoolean) {
            return;
        }
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}