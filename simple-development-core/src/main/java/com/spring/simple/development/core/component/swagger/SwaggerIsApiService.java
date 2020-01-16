package com.spring.simple.development.core.component.swagger;

import com.alibaba.fastjson.JSONObject;
import com.spring.simple.development.core.annotation.base.IsApiMethodService;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.baseconfig.isapiservice.ServiceInvoke;
import com.spring.simple.development.core.component.mvc.req.RpcRequest;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.utils.DateUtils;
import com.spring.simple.development.support.utils.HttpRequestUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author liko.wang
 * @Date 2020/1/15/015 17:52
 * @Description //TODO
 **/
public class SwaggerIsApiService {

    public static List<String> getIsApiServiceTransformControllerCodes() {
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
                String methodParam = baseUrl + "/"+className+"/"+methodName;
                if (login) {
                    methodParam = methodParam + ","+className+","+ method.getName() + "," + "invokeConfigService";
                } else {
                    methodParam = methodParam + ","+className+","+ method.getName() + "," + "invokeService";
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

}