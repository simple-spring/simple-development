package com.spring.simple.development.core.component.swagger;

import com.spring.simple.development.core.component.mvc.controller.ApiController;
import com.spring.simple.development.core.init.AppInitializer;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.DateUtils;
import com.spring.simple.development.support.utils.GroovyClassLoaderUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author liko.wang
 * @Date 2020/1/15/015 10:11
 * @Description SwaggerConfig
 **/
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

    public SwaggerConfig() throws Exception {
        List<String> isApiServiceTransformControllerCodes = SwaggerIsApiService.getIsApiServiceTransformControllerCodes();
        if (!CollectionUtils.isEmpty(isApiServiceTransformControllerCodes)) {
            if (!CollectionUtils.isEmpty(isApiServiceTransformControllerCodes)) {
                for (String code : isApiServiceTransformControllerCodes) {
                    Class aClass = GroovyClassLoaderUtils.loadNewInstance(code);
                    this.addMapping(aClass);
                }
            }
        }
    }

    private String title = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_TITLE);
    private String description = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_DESCRIPTION);
    private String contact = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_CONTACT);
    private String version = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_VERSION);
    private String url = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_URL);

    @Bean
    public Docket buildDocket() throws Exception {

        Docket build = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any()) // and by paths
                .build();
        return build;

    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        String isEnable = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_IS_ENABLE);
        boolean isEnableBoolean = Boolean.parseBoolean(isEnable);
        // 不启动
        if (!isEnableBoolean) {
            return;
        }
        // 解决静态资源无法访问
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        // 解决swagger无法访问
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        // 解决swagger的js文件无法访问
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(url)
                .contact(contact)
                .version(version)
                .build();
    }

    public void addMapping(Class loadClass) throws Exception {
        while (true) {
            RequestMappingHandlerMapping bean = (RequestMappingHandlerMapping)AppInitializer.rootContext.getBean("requestMappingHandlerMapping");
            if (bean == null) {
                System.out.println("等待自动注册mapping"+ DateUtils.getCurrentTime());
                Thread.sleep(3000);
            } else {
                break;
            }
        }
        RequestMappingHandlerMapping requestMappingHandlerMapping = AppInitializer.rootContext.getBean(RequestMappingHandlerMapping.class);
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) AppInitializer.rootContext.getAutowireCapableBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(loadClass);
        String simpleName = loadClass.getSimpleName();
        defaultListableBeanFactory.registerBeanDefinition(simpleName, beanDefinitionBuilder.getBeanDefinition());
        Method method = requestMappingHandlerMapping.getClass().getSuperclass().getSuperclass().getDeclaredMethod("detectHandlerMethods", Object.class);
        method.setAccessible(true);
        method.invoke(requestMappingHandlerMapping, simpleName);

    }

    public void removeMapping(Class beanClass) {
        RequestMappingHandlerMapping requestMappingHandlerMapping = AppInitializer.rootContext.getBean(RequestMappingHandlerMapping.class);
        Object controller = AppInitializer.rootContext.getBeanNamesForType(beanClass);
        if (controller == null) {
            System.out.println("spring容器中已不存在该实体");
        }
        Class<?> targetClass = controller.getClass();
        ReflectionUtils.doWithMethods(targetClass, new ReflectionUtils.MethodCallback() {
            @Override
            public void doWith(Method method) {
                Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
                try {
                    Method createMappingMethod = RequestMappingHandlerMapping.class.
                            getDeclaredMethod("getMappingForMethod", Method.class, Class.class);
                    createMappingMethod.setAccessible(true);
                    RequestMappingInfo requestMappingInfo = (RequestMappingInfo)
                            createMappingMethod.invoke(requestMappingHandlerMapping, specificMethod, targetClass);
                    if (requestMappingInfo != null) {
                        requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, ReflectionUtils.USER_DECLARED_METHODS);
    }
}