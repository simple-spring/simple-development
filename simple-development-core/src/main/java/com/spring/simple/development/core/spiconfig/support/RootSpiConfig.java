package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.SpringSimpleApplication;
import com.spring.simple.development.core.component.root.RootConfig;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.utils.ClassLoadUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Spi(configName = "SpringSimpleApplication")
public class RootSpiConfig implements SimpleSpiConfig<SpringSimpleApplication, RootConfig> {
    @Override
    public Class<RootConfig> getConfigClass(SpringSimpleApplication springSimpleApplication) {
        try {
            // 修改RootConfig ComponentScan扫描包的路径
            List<String> packageNames = new ArrayList<>();
            // 如果为空则选用默认值
            if (StringUtils.isEmpty(springSimpleApplication.appPackagePathName())) {
                packageNames.add(System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME));
            } else {
                packageNames.add(springSimpleApplication.appPackagePathName());
            }
            packageNames.add(SystemProperties.SPRING_SIMPLE_BASE_PACKAGE_PATH);
            Class rootConfig = ClassLoadUtil.javassistCompile(RootConfig.class, "org.springframework.context.annotation.ComponentScan", packageNames, "basePackages");
            return rootConfig;
        } catch (Exception ex) {
            throw new RuntimeException("RootConfig initialization failed", ex);
        }
    }
}
