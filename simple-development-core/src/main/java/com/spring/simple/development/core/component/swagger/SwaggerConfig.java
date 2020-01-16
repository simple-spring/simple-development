package com.spring.simple.development.core.component.swagger;

import com.spring.simple.development.core.annotation.base.IsApiMethodService;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.init.AppInitializer;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.GroovyClassLoaderUtils;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author liko.wang
 * @Date 2020/1/15/015 10:11
 * @Description SwaggerConfig
 **/
@EnableSwagger2
public class SwaggerConfig implements ApplicationListener<ContextRefreshedEvent> {
    public SwaggerConfig() {

    }

    private String title = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_TITLE);
    private String description = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_DESCRIPTION);
    private String contact = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_CONTACT);
    private String version = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_VERSION);
    private String url = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_URL);

    @Bean
    public Docket buildDocket() {
        // 动态注册controller
        InvokeSwaggerIsApiService();
        Docket build = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any()) // and by paths
                .build();
        return build;

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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        // 动态注册controller
        //InvokeSwaggerIsApiService();
    }

    public void InvokeSwaggerIsApiService() {
        try {
            List<String> isApiServiceTransformControllerCodes = getIsApiServiceTransformControllerCodes();
            if (!CollectionUtils.isEmpty(isApiServiceTransformControllerCodes)) {
                if (!CollectionUtils.isEmpty(isApiServiceTransformControllerCodes)) {
                    for (String code : isApiServiceTransformControllerCodes) {
                        Class aClass = GroovyClassLoaderUtils.loadNewInstance(code);
                        this.addMapping(aClass);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("动态注入失败");
            throw new RuntimeException(e);
        }
    }

    public List<String> getIsApiServiceTransformControllerCodes() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME)) // 指定路径URL
                .addScanners(new MethodAnnotationsScanner()) // 添加 方法注解扫描工具
        );
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(IsApiService.class);
        if (CollectionUtils.isEmpty(typesAnnotatedWith)) {
            return null;
        }
        List<String> codes = new ArrayList<>();
        // 类上面的地址默认
        String baseUrl = "/data/api/v1";
        for (Class isApiClass : typesAnnotatedWith) {
            Method[] declaredMethods = isApiClass.getDeclaredMethods();
            if (declaredMethods == null || declaredMethods.length == 0) {
                continue;
            }
            // 方法上面的地址
            String className;
            Annotation annotation = isApiClass.getAnnotation(IsApiService.class);
            IsApiService isApiService = (IsApiService) annotation;
            String defaultMethodRequestMappingPath = isApiService.value();
            if (!StringUtils.isEmpty(defaultMethodRequestMappingPath)) {
                className = defaultMethodRequestMappingPath;
            } else {
                className = isApiClass.getInterfaces()[0].getSimpleName();
            }
            List<String> methodParams = new ArrayList<>();
            for (Method method : declaredMethods) {
                NoApiMethod noApiMethod = method.getAnnotation(NoApiMethod.class);
                if (noApiMethod != null) {
                    continue;
                }
                String methodName = method.getName();
                IsApiMethodService isApiMethodService = method.getAnnotation(IsApiMethodService.class);
                if (isApiMethodService != null) {
                    String defaultMethodValue = isApiMethodService.value();
                    if (!StringUtils.isEmpty(defaultMethodValue)) {
                        methodName = defaultMethodValue;
                    }
                }

                boolean login = isApiService.isLogin();
                String methodParam = baseUrl + "/" + className + "/" + methodName;
                if (login) {
                    methodParam = methodParam + "," + className + "," + method.getName() + "," + "invokeConfigService";
                } else {
                    methodParam = methodParam + "," + className + "," + method.getName() + "," + "invokeService";
                }
                methodParams.add(methodParam);

            }
            if (CollectionUtils.isEmpty(methodParams)) {
                continue;
            }
            String baseCode = CodeGenerationHandler.getBaseCode(isApiClass.getSimpleName(), methodParams);
            codes.add(baseCode);
        }
        return codes;
    }

    public void addMapping(Class loadClass) throws Exception {
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) AppInitializer.rootContext.getBean("requestMappingHandlerMapping");
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) AppInitializer.rootContext.getAutowireCapableBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(loadClass);
        String simpleName = loadClass.getSimpleName();
        defaultListableBeanFactory.registerBeanDefinition(simpleName, beanDefinitionBuilder.getBeanDefinition());
        Method method = requestMappingHandlerMapping.getClass().getSuperclass().getSuperclass().getDeclaredMethod("detectHandlerMethods", Object.class);
        method.setAccessible(true);
        method.invoke(requestMappingHandlerMapping, simpleName);
    }


}