package com.spring.simple.development.core.component.swagger;

import com.spring.simple.development.core.annotation.base.IsApiMethodService;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.support.constant.SystemProperties;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
        String classRequestMappingPath = "/data/api/v1";
        for (Class isApiClass : typesAnnotatedWith) {
            // 方法上面的地址
            String methodRequestMappingPath;
            Annotation annotation = isApiClass.getAnnotation(IsApiService.class);
            IsApiService isApiService = (IsApiService) annotation;
            String defaultMethodRequestMappingPath = isApiService.value();
            if (StringUtils.isEmpty(defaultMethodRequestMappingPath)) {
                methodRequestMappingPath = defaultMethodRequestMappingPath;
            } else {
                methodRequestMappingPath = isApiClass.getClass().getInterfaces()[0].getSimpleName();
            }

            // 包名
            String code = "package com.spring.simple.development.demo.controller;\n" +
                    "\n" +
                    "import org.springframework.web.bind.annotation.*;\n" +
                    "\n" +
                    "@RestController\n" +
                    "@RequestMapping(\"/data/api/v1\")\n" +
                    "public class " + isApiClass.getSimpleName() + "Controller {";

            Method[] declaredMethods = isApiClass.getDeclaredMethods();
            for (Method method : declaredMethods) {
                NoApiMethod noApiMethod = method.getAnnotation(NoApiMethod.class);
                if (noApiMethod != null) {
                    continue;
                }
                IsApiMethodService isApiMethodService = method.getAnnotation(IsApiMethodService.class);
                String defaultMethodValue = isApiMethodService.value();
                if (StringUtils.isEmpty(defaultMethodValue)) {
                    methodRequestMappingPath = methodRequestMappingPath + "/" + method.getName();
                } else {
                    methodRequestMappingPath = methodRequestMappingPath + "/" + defaultMethodValue;

                }
                code += "@RequestMapping(value = " + methodRequestMappingPath + ",method = RequestMethod.POST)\n" +
                        "                public String index1() {\n" +
                        "                    return \"index1\";\n" +
                        "                }";
            }
            code += code + "}";
            codes.add(code);
        }
        return codes;
    }

}