package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableDubbo;
import com.spring.simple.development.core.init.SpringBootAppInitializer;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;
import com.spring.simple.development.support.constant.PackageNameConstant;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.ClassLoadUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liko wang
 */
@Spi(configName = "EnableDubbo")
public class DubboSpiConfig implements SimpleSpiConfig<EnableDubbo> {

    public static List<String> dubboPackageNames = new ArrayList<>();

    @Override
    public Class getConfigClass(EnableDubbo enableDubbo) {
        try {
            // dubbo参数校验
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_APPLICATION_NAME));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_REGISTRY_ADDRESS));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_PROTOCOL_NAME));
            Assert.notNull(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_PROTOCOL_PORT));
            // 添加DataSourceConfig MapperScan扫描包的路径
            // 默认包路径
            String basePackageName = System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME);
            if (StringUtils.isEmpty(enableDubbo.dubboPackage())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_DUBBO_PACKAGE, basePackageName + PackageNameConstant.DUBBO);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_DUBBO_PACKAGE, enableDubbo.dubboPackage());
            }
            // 启动shiro
            boolean isEnableBoolean = Boolean.parseBoolean(PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_SHIRO_ISOPEN));
            if (isEnableBoolean) {
                dubboPackageNames.add("com.acl.support.auth.web.authz");
                dubboPackageNames.add("com.acl.support.auth.web.interceptor");
            }
            dubboPackageNames.add(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_DUBBO_PACKAGE));
            // 添加自定义拦截器spi文件
            //createDubboFilter();
            Class<?> dubboClass = Class.forName("com.spring.simple.development.core.component.dubbo.DubboConfig");
            Class dubboConfig = ClassLoadUtil.javassistCompile(dubboClass, "com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan", dubboPackageNames, "basePackages");

            dubboPackageNames.add(dubboClass.getPackage().getName());
            SpringBootAppInitializer.packageNames.addAll(dubboPackageNames);
            return dubboConfig;
        } catch (Exception ex) {
            throw new RuntimeException("RootConfig initialization failed", ex);
        }
    }

    private void createDubboFilter() throws IOException {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        File resourceFile = new File(path);
        if (resourceFile.exists() == false) {
            throw new RuntimeException("createDubboFilter file failed");
        }
        File filterFile = new File(path + "/META-INF/dubbo/com.alibaba.dubbo.rpc.Filter");
        if (filterFile.exists()) {
            return;
        }
        File metaInfFile = new File(path + "/META-INF");
        if (metaInfFile.exists() == false) {
            metaInfFile.mkdirs();
        }
        File dubboFile = new File(path + "/META-INF/dubbo");
        if (dubboFile.exists() == false) {
            dubboFile.mkdirs();
        }
        BufferedWriter out = new BufferedWriter(new FileWriter(path + "/META-INF/dubbo/com.alibaba.dubbo.rpc.Filter"));
        out.write("springDubboExceptionFilter=com.spring.simple.development.core.component.dubbo.SpringSimpleDubboExceptionFilter");
        out.close();

    }
}
