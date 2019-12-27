package com.spring.simple.development.core.config.support;

import com.spring.simple.development.core.annotation.Spi;
import com.spring.simple.development.core.annotation.config.EnableWebMvc;
import com.spring.simple.development.core.config.SimpleSpiConfig;
import com.spring.simple.development.core.constant.PackageNameConstant;
import com.spring.simple.development.core.constant.SystemProperties;
import com.spring.simple.development.core.mvc.WebConfig;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.ClassLoadUtil;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liko wang
 */
@Spi(configName = "EnableWebMvc")
public class WebMvcSpiConfig implements SimpleSpiConfig<EnableWebMvc, WebConfig> {
    @Override
    public Class<WebConfig> getConfigClass(EnableWebMvc enableWebMvc) {
        try {
            String basePackageName = System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APPPACKAGEPATHNAME);

            // 配置参数
            if (StringUtils.isEmpty(enableWebMvc.interceptorPackagePath())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MVC_CONFIG_INTERCEPTORPATH, basePackageName + PackageNameConstant.CONTROLLER);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MVC_CONFIG_INTERCEPTORPATH, enableWebMvc.interceptorPackagePath());
            }
            if (StringUtils.isEmpty(enableWebMvc.urlPath())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MVC_CONFIG_URLPATH, "/");
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MVC_CONFIG_URLPATH, enableWebMvc.urlPath());
            }
            if (StringUtils.isEmpty(enableWebMvc.packagePath())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MVC_CONFIG_PACKAGEPATH, "");
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_MVC_CONFIG_PACKAGEPATH, enableWebMvc.packagePath());
            }

            // 修改WebConfig ComponentScan扫描包的路径
            List<String> mvcPackageNames = new ArrayList<>();
            // 支持isApiConfig,默认注册
            mvcPackageNames.add("com.spring.simple.development.core.mvc.controller");
            String packagePaths = enableWebMvc.packagePath();
            if (StringUtils.isNotBlank(packagePaths)) {
                String[] paths = packagePaths.split(",");
                for (String path : paths) {
                    mvcPackageNames.add(path);
                }
            }
            Class webConfigClass = ClassLoadUtil.javassistCompile(WebConfig.class, "org.springframework.context.annotation.ComponentScan", mvcPackageNames, "basePackages");
            return webConfigClass;
        } catch (Exception ex) {
            throw new RuntimeException("RootConfig initialization failed", ex);
        }
    }
}
