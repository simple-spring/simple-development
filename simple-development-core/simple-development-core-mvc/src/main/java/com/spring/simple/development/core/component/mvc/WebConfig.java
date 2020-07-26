package com.spring.simple.development.core.component.mvc;

import com.spring.simple.development.core.annotation.base.SimpleInterceptor;
import com.spring.simple.development.core.component.mvc.interceptor.ApiSupportInterceptor;
import com.spring.simple.development.core.component.shiro.cas.ShiroCasInterceptor;
import com.spring.simple.development.core.component.shiro.cas.ShiroLavaSupportInterceptor;
import com.spring.simple.development.core.handler.listener.SimpleComponentListener;
import com.spring.simple.development.core.init.AppInitializer;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static com.spring.simple.development.support.exception.GlobalResponseCode.SERVICE_NOT_EXIST;

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

    protected DispatcherServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
        final DispatcherServlet dispatcherServlet = new DispatcherServlet() {
            @Override
            protected HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
                if (this.getHandlerMappings() != null) {
                    for (HandlerMapping hm : this.getHandlerMappings()) {
                        if (logger.isTraceEnabled()) {
                            logger.trace(
                                    "Testing handler map [" + hm + "] in DispatcherServlet with name '" + getServletName() + "'");
                        }
                        HandlerExecutionChain handler = hm.getHandler(request);
                        if (handler != null && !(handler.getHandler() instanceof DefaultServletHttpRequestHandler)) {
                            return handler;
                        }
                    }
                }
                return null;
            }

            @Override
            protected void noHandlerFound(HttpServletRequest request, HttpServletResponse response) throws Exception {

                //如果是ajax请求响应头会有x-requested-with
                if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
                    throw new GlobalException(SERVICE_NOT_EXIST);
                }else{
                    response.sendRedirect("/404.html");
                }
            }
            {
                setApplicationContext(servletAppContext);
            }
        };
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
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
        resourceViewResolver.setSuffix(".html");
        resourceViewResolver.setExposeContextBeansAsAttributes(true);
        return resourceViewResolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        super.configureDefaultServletHandling(configurer);
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
        AnnotationConfigWebApplicationContext rootContext = AppInitializer.rootContext;
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) rootContext.getAutowireCapableBeanFactory();
        // 默认拦截器
        String[] excludes = new String[11];
        excludes[0] = "/swagger-ui.html";
        excludes[1] = "/webjars/**";
        excludes[2] = "/swagger-resources";
        excludes[3] = "/swagger-resources/configuration/ui";
        excludes[4] = "/swagger-resources/configuration/security";
        excludes[5] = "/simpleDoc/index.html";
        excludes[6] = "/assets/**";
        excludes[7] = "/simpleDoc/**";
        excludes[8] = "/simple-spring.png";
        excludes[9] = "/doc.html";
        excludes[10] = "/404.html";

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
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", createDispatcherServlet(rootContext));
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
        // 解决simple文档无法访问
        registry.addResourceHandler("/simpleDoc/index.html")
                .addResourceLocations("classpath:/META-INF/");
        // 解决simple文档无法访问
        registry.addResourceHandler("/404.html")
                .addResourceLocations("classpath:/META-INF/simpleDoc/404.html");
        // 解决simple文档无法访问
        registry.addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/META-INF/simpleDoc/assets/");
        // 解决simple文档无法访问
        registry.addResourceHandler("/simple-spring.png")
                .addResourceLocations("classpath:/META-INF/simpleDoc/simple-spring.png");
        // 解决simple文档无法访问
        registry.addResourceHandler("/simpleDoc/**")
                .addResourceLocations("classpath:/META-INF/simpleDoc/simpleDoc/");
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger无法访问
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}