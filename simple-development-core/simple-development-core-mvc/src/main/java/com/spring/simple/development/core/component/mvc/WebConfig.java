package com.spring.simple.development.core.component.mvc;

import com.jc.support.auth.web.interceptor.DefaultBizAuthenticationHandlerInterceptor;
import com.jc.xauth.web.interceptor.AuthenticationHandlerInterceptor;
import com.spring.simple.development.core.annotation.base.SimpleInterceptor;
import com.spring.simple.development.core.component.mvc.interceptor.ApiSupportInterceptor;
import com.spring.simple.development.core.handler.listener.SimpleComponentListener;
import com.spring.simple.development.core.init.AppInitializer;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.util.Set;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 12:49
 * @Description //TODO
 **/
@Configurable
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter implements SimpleComponentListener {
    public WebConfig() {

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
        super.configureDefaultServletHandling(configurer);
        configurer.enable();
    }

    @Bean
    public ApiSupportInterceptor getApiSupportInterceptor(){
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
        AnnotationConfigWebApplicationContext rootContext = AppInitializer.rootContext;
        DefaultListableBeanFactory defaultListableBeanFactory =(DefaultListableBeanFactory) rootContext.getAutowireCapableBeanFactory();
        // 默认拦截器
        String[] excludes = new String[5];
        excludes[0] = "/swagger-ui.html";
        excludes[1] = "/webjars/**";
        excludes[2] = "/swagger-resources";
        excludes[3] = "/swagger-resources/configuration/ui";
        excludes[4] = "/swagger-resources/configuration/security";
        // 启动shiro
        boolean isEnableBoolean = Boolean.parseBoolean(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_ISOPEN));
        if(isEnableBoolean){
            // 解决自定义拦截器中无法注入bean
            //创建bean信息
            BeanDefinitionBuilder definitionBuilderDefaultBizAuthenticationHandlerInterceptor = BeanDefinitionBuilder.genericBeanDefinition(DefaultBizAuthenticationHandlerInterceptor.class);
            BeanDefinitionBuilder definitionBuilderAuthenticationHandlerInterceptor = BeanDefinitionBuilder.genericBeanDefinition(AuthenticationHandlerInterceptor.class);
            //动态注册bean
            defaultListableBeanFactory.registerBeanDefinition(DefaultBizAuthenticationHandlerInterceptor.class.getSimpleName(),definitionBuilderDefaultBizAuthenticationHandlerInterceptor.getBeanDefinition());
            //动态注册bean
            defaultListableBeanFactory.registerBeanDefinition(AuthenticationHandlerInterceptor.class.getSimpleName(),definitionBuilderAuthenticationHandlerInterceptor.getBeanDefinition());

            // 获取bean
            DefaultBizAuthenticationHandlerInterceptor beanDefaultBizAuthenticationHandlerInterceptor = (DefaultBizAuthenticationHandlerInterceptor)rootContext.getBean(DefaultBizAuthenticationHandlerInterceptor.class.getSimpleName());
            AuthenticationHandlerInterceptor beanAuthenticationHandlerInterceptor = (AuthenticationHandlerInterceptor)rootContext.getBean(AuthenticationHandlerInterceptor.class.getSimpleName());
            registry.addInterceptor(beanDefaultBizAuthenticationHandlerInterceptor).addPathPatterns("/**").excludePathPatterns(excludes);;
            registry.addInterceptor(beanAuthenticationHandlerInterceptor).addPathPatterns("/**").excludePathPatterns(excludes);
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
                defaultListableBeanFactory.registerBeanDefinition(clazz.getSimpleName(),beanDefinitionBuilder.getBeanDefinition());
                System.out.println("SimpleInterceptor class: " + clazz.getName());
                // 获取bean
                Object bean = rootContext.getBean(clazz.getSimpleName());
                registry.addInterceptor((HandlerInterceptor) bean).excludePathPatterns(excludes);
            }

        } catch (Exception e) {
            throw new RuntimeException("mvc 拦截器注册失败", e);
        }
    }

    @Override
    public void onApplicationEvent(ServletContext servletContext, AnnotationConfigWebApplicationContext rootContext) {
        // 注册请求分发器
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MVC_CONFIG_URL_PATH));
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