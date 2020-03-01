package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableWebMvc;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;
import com.spring.simple.development.support.constant.PackageNameConstant;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.ClassLoadUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liko wang
 */
@Spi(configName = "EnableWebMvc")
public class WebMvcSpiConfig implements SimpleSpiConfig<EnableWebMvc> {
    @Override
    public Class getConfigClass(EnableWebMvc enableWebMvc) {
        try {
            String basePackageName = System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME);

            // 配置参数
            if (StringUtils.isEmpty(enableWebMvc.interceptorPackagePath())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MVC_CONFIG_INTERCEPTOR_PATH, basePackageName + PackageNameConstant.INTERCEPTOR);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MVC_CONFIG_INTERCEPTOR_PATH, enableWebMvc.interceptorPackagePath());
            }
            if (StringUtils.isEmpty(enableWebMvc.urlPath())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MVC_CONFIG_URL_PATH, "/");
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MVC_CONFIG_URL_PATH, enableWebMvc.urlPath());
            }
            if (StringUtils.isEmpty(enableWebMvc.packagePath())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MVC_CONFIG_PACKAGE_PATH, basePackageName + PackageNameConstant.CONTROLLER);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MVC_CONFIG_PACKAGE_PATH, enableWebMvc.packagePath());
            }

            // 修改WebConfig ComponentScan扫描包的路径
            List<String> mvcPackageNames = new ArrayList<>();
            // 支持isApiConfig,默认注册
            mvcPackageNames.add("com.spring.simple.development.core.component.mvc.controller");
            String[] paths = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MVC_CONFIG_PACKAGE_PATH).split(",");
            for (String path : paths) {
                mvcPackageNames.add(path);
            }
            Class<?> targetWebConfigClass = Class.forName("com.spring.simple.development.core.component.mvc.WebConfig");
            Class webConfigClass = ClassLoadUtil.javassistCompile(targetWebConfigClass, "org.springframework.context.annotation.ComponentScan", mvcPackageNames, "basePackages");
            return webConfigClass;
        } catch (Exception ex) {
            throw new RuntimeException("RootConfig initialization failed", ex);
        }
    }
}
