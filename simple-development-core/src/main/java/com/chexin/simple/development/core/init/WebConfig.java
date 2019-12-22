package com.chexin.simple.development.core.init;

import com.chexin.simple.development.core.constant.PackageNameConstant;
import com.chexin.simple.development.core.mvc.interceptor.ApiSupportInterceptor;
import com.chexin.simple.development.support.properties.PropertyConfigurer;
import com.chexin.simple.development.support.utils.PackageUtil;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 12:49
 * @Description //TODO
 **/
@Configurable
@EnableWebMvc
@ComponentScan(basePackages = {"com.chexin.simple.development.core.mvc.controller"})
public class WebConfig extends WebMvcConfigurerAdapter {
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

    /**
     * @param registry
     * @return void
     * @author liko.wang
     * @Date 2019/12/19/019 13:27
     * @Description 注册拦截器
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 默认拦截器
        registry.addInterceptor(new ApiSupportInterceptor());
        try {
            List<String> classNames = PackageUtil.getClassName(PropertyConfigurer.getProperty("spring.base.package") + PackageNameConstant.INTERCEPTOR);
            if (CollectionUtils.isEmpty(classNames)) {
                return;
            }
            for (String className : classNames) {
                Class<?> aClass = Class.forName(className);
                Object interceptor = aClass.newInstance();
                registry.addInterceptor((HandlerInterceptor) interceptor);
            }

        } catch (Exception e) {
            throw new RuntimeException("mvc 拦截器注册失败", e);
        }
    }
}