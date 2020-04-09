package com.spring.simple.development.core.component.swagger;

import com.google.common.collect.Lists;
import com.spring.simple.development.core.annotation.base.IsApiMethodService;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.annotation.base.swagger.Api;
import com.spring.simple.development.core.annotation.base.swagger.ApiImplicitParam;
import com.spring.simple.development.core.annotation.base.swagger.ApiOperation;
import com.spring.simple.development.core.baseconfig.isapiservice.MethodParams;
import com.spring.simple.development.core.init.AppInitializer;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.GroovyClassLoaderUtils;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author liko.wang
 * @Date 2020/1/15/015 10:11
 * @Description SwaggerConfig
 **/
public class SwaggerConfig {
    public SwaggerConfig() {

    }

    private String title = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_TITLE);
    private String description = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_DESCRIPTION);
    private String contact = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_CONTACT);
    private String version = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_VERSION);
    private String url = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_URL);
    private String headerParams = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_HEADER_PARAMS);
    private String headerParamsDescription = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_SWAGGER_HEADER_DESCRIPTION);

    @Bean
    public Docket buildDocket() {

        // 动态注册controller
        InvokeSwaggerIsApiService();
        //添加header参数
        ParameterBuilder ticketPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        String[] headerParamsStr = headerParams.split(",");
        String[] headerParamsDescriptionStr = headerParamsDescription.split(",");
        if (headerParamsStr.length != headerParamsDescriptionStr.length) {
            throw new RuntimeException("swagger headerParams size init  fail");
        }
        for (int i = 0; i < headerParamsStr.length; i++) {
            ticketPar.name(headerParamsStr[i].split(":")[0]).defaultValue(headerParamsStr[i].split(":")[1]).description(headerParamsDescriptionStr[i])
                    .modelRef(new ModelRef("string")).parameterType("header")
                    .required(false).build();
            pars.add(ticketPar.build());
        }

        Docket build = new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars)
                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()));
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

    private ApiKey apiKey() {
        return new ApiKey("token", "token", "header"
        );
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("^((?!login).)+$")).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("token",
                authorizationScopes));
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
        System.out.println("reflections IsApiService start");
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                // 指定路径URL
                .forPackages(System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME))
                // 添加 方法注解扫描工具
                .addScanners(new MethodAnnotationsScanner())
        );
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(IsApiService.class);
        System.out.println("reflections IsApiService end");

        if (CollectionUtils.isEmpty(typesAnnotatedWith)) {
            return null;
        }
        List<String> codes = new ArrayList<>();

        for (Class isApiClass : typesAnnotatedWith) {
            Method[] declaredMethods = isApiClass.getDeclaredMethods();
            if (declaredMethods == null || declaredMethods.length == 0) {
                continue;
            }
            // 方法上面的地址
            CodeGenerationParams codeGenerationParams = new CodeGenerationParams();
            String className;
            Annotation annotation = isApiClass.getAnnotation(IsApiService.class);
            IsApiService isApiService = (IsApiService) annotation;
            String defaultMethodRequestMappingPath = isApiService.value();
            if (!StringUtils.isEmpty(defaultMethodRequestMappingPath)) {
                className = defaultMethodRequestMappingPath;
            } else {
                className = isApiClass.getInterfaces()[0].getSimpleName();
            }
            Api api = (Api) isApiClass.getAnnotation(Api.class);
            codeGenerationParams.setServiceName(className);
            codeGenerationParams.setServiceNameLog(className);
            if (api != null) {
                codeGenerationParams.setClassTags(api.tags());
            }

            List<CodeGenerationMethodParams> codeGenerationMethodParamsList = new ArrayList<>();
            for (Method method : declaredMethods) {
                NoApiMethod noApiMethod = method.getAnnotation(NoApiMethod.class);
                if (noApiMethod != null) {
                    continue;
                }
                CodeGenerationMethodParams codeGenerationMethodParams = new CodeGenerationMethodParams();

                String methodName = method.getName();
                IsApiMethodService isApiMethodService = method.getAnnotation(IsApiMethodService.class);
                if (isApiMethodService != null) {
                    String defaultMethodValue = isApiMethodService.value();
                    if (!StringUtils.isEmpty(defaultMethodValue)) {
                        methodName = defaultMethodValue;
                    }
                }
                // 类上面的地址默认
                String baseUrl = "/data/api/v1";
                boolean login = isApiService.isLogin();
                if (login) {
                    codeGenerationMethodParams.setIsLogin("");
                    codeGenerationMethodParams.setInvokeMethodName("invokeService");
                } else {
                    baseUrl = "/data/config/v1";
                    codeGenerationMethodParams.setIsLogin("@NoLogin");
                    codeGenerationMethodParams.setInvokeMethodName("invokeConfigService");
                }
                codeGenerationMethodParams.setMethodName(methodName);
                codeGenerationMethodParams.setMappingUrl(baseUrl + "/" + className + "/" + methodName);

                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes != null) {
                    String[] paramsKey = new String[parameterTypes.length];
                    Class<?>[] methodClass = new Class[parameterTypes.length];

                    for (int i = 0; i < parameterTypes.length; i++) {
                        String simpleName = parameterTypes[i].getSimpleName();
                        String paramKey = toLowerCaseFirstOne(simpleName);
                        paramsKey[i] = paramKey;
                        methodClass[i] = parameterTypes[i];
                    }
                    MethodParams methodParams = new MethodParams();
                    methodParams.setKey(paramsKey);
                    methodParams.setMethodClass(methodClass);
                    ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
                    if (apiOperation != null) {
                        codeGenerationMethodParams.setApiOperationValue(apiOperation.value());
                        codeGenerationMethodParams.setApiOperationValueNotes(apiOperation.notes());
                    }
                    ApiImplicitParam apiImplicitParam = method.getAnnotation(ApiImplicitParam.class);
                    if (apiImplicitParam != null) {
                        codeGenerationMethodParams.setApiImplicitParamName(apiImplicitParam.name());
                        codeGenerationMethodParams.setApiImplicitParamValue(apiImplicitParam.description());
                        codeGenerationMethodParams.setApiImplicitParamDataType(apiImplicitParam.dataType());
                        Class aClass = apiImplicitParam.resultDataType();
                        Class pageClass = apiImplicitParam.PageResultDataType();
                        codeGenerationMethodParams.setResultDataType(aClass.getSimpleName());
                        codeGenerationMethodParams.setPageResultDataType(pageClass.getSimpleName());
                        String resultPackagePath = aClass.getPackage().getName() + "." + aClass.getSimpleName() + ";";
                        String pageResultPackagePath = pageClass.getPackage().getName() + "." + pageClass.getSimpleName() + ";";
                        codeGenerationMethodParams.setResultDataTypePackagePath(resultPackagePath);
                        codeGenerationMethodParams.setResultDataTypePackagePath(pageResultPackagePath);
                        codeGenerationParams.setParamTypePackagePath(codeGenerationParams.getParamTypePackagePath() + "\n" + "import " + resultPackagePath);
                    }
                    if (parameterTypes.length > 0) {
                        codeGenerationMethodParams.setRequestBodyType(parameterTypes[0].getSimpleName() + " ");
                        codeGenerationMethodParams.setRequestBodyName(toLowerCaseFirstOne(parameterTypes[0].getSimpleName()));
                        Package aPackage = parameterTypes[0].getPackage();
                        String name = aPackage.getName() + "." + parameterTypes[0].getSimpleName() + ";";
                        codeGenerationParams.setParamTypePackagePath(codeGenerationParams.getParamTypePackagePath() + "\n" + "import " + name);
                    }else{
                        codeGenerationMethodParams.setRequestBodyType(" ");
                        codeGenerationMethodParams.setRequestBodyName(" ");
                    }
                    codeGenerationMethodParamsList.add(codeGenerationMethodParams);
                }
            }
            codeGenerationParams.setCodeGenerationMethodParams(codeGenerationMethodParamsList);
            String baseCode = CodeGenerationHandler.getBaseCode(codeGenerationParams);
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

    /**
     * 首字母转大写
     *
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.isUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

}