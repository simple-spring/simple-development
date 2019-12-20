package com.chexin.simple.development.core.init;

import com.chexin.simple.development.core.mvc.interceptor.ApiSupportInterceptor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 12:49
 * @Description //TODO
 **/
@Configurable
@EnableWebMvc
@ComponentScan("com.chexin.simple.development.core.mvc.controller")
public class WebConfig extends WebMvcConfigurerAdapter {
    /**
     * @author liko.wang
     * @Date 2019/12/19/019 13:28
     * @param
     * @return org.springframework.web.servlet.ViewResolver
     * @Description 注册视图解析器
     **/
    @Bean
    public ViewResolver viewResolver(){
        InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
        resourceViewResolver.setPrefix("/WEB-INF/views/");
        resourceViewResolver.setSuffix(".jsp");
        resourceViewResolver.setExposeContextBeansAsAttributes(true);
        return resourceViewResolver;
    }
    @Bean
    public ApiSupportInterceptor apiSupportInterceptor(){
        return new ApiSupportInterceptor();
    }
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        super.configureDefaultServletHandling(configurer);
        configurer.enable();
    }
    /**
     * @author liko.wang
     * @Date 2019/12/19/019 13:27
     * @param registry
     * @return void
     * @Description 注册拦截器
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiSupportInterceptor());

    }
}