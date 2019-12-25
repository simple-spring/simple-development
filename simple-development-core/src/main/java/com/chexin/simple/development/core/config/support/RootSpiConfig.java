package com.chexin.simple.development.core.config.support;

import com.chexin.simple.development.core.annotation.Spi;
import com.chexin.simple.development.core.annotation.config.SpringSimpleApplication;
import com.chexin.simple.development.core.config.SimpleSpiConfig;
import com.chexin.simple.development.core.constant.SystemProperties;
import com.chexin.simple.development.core.init.RootConfig;
import com.chexin.simple.development.support.utils.ClassLoadUtil;
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
            if (StringUtils.isEmpty(springSimpleApplication.applicationName())) {
                packageNames.add(System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APPPACKAGEPATHNAME));
            } else {
                packageNames.add(springSimpleApplication.applicationName());
            }
            Class rootConfig = ClassLoadUtil.javassistCompile(RootConfig.class, "org.springframework.context.annotation.ComponentScan", packageNames, "basePackages");
            return rootConfig;
        } catch (Exception ex) {
            throw new RuntimeException("RootConfig initialization failed", ex);
        }
    }
}
