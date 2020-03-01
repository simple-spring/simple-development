package com.spring.simple.development.core.spiconfig.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableXxlJob;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;
import com.spring.simple.development.support.constant.PackageNameConstant;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.ClassLoadUtil;


/**
 * @author ys
 * @Date 2019年12月27日
 * @Description TODO
 */
@Spi(configName = "EnableXxlJob")
public class XxlJobSpiConfig implements SimpleSpiConfig<EnableXxlJob> {

    @Override
    public Class getConfigClass(EnableXxlJob enableXxlJob) {
        try {
            PropertyConfigurer.setProperty(SystemProperties.APPLICATION_XXLJOB_CONFIG_LOGPATH, enableXxlJob.logPath());
            PropertyConfigurer.setProperty(SystemProperties.APPLICATION_XXLJOB_CONFIG_LOGRETENTIONDAYS, enableXxlJob.logRetentionDays());
            PropertyConfigurer.setProperty(SystemProperties.APPLICATION_XXLJOB_CONFIG_ACCESSTOKEN, enableXxlJob.accessToken());
            // 添加DataSourceConfig MapperScan扫描包的路径
            // 默认包路径
            String basePackageName = System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME);
            if (StringUtils.isEmpty(enableXxlJob.jobPackage())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_XXLJOB_CONFIG_JOBPACKAGE, basePackageName + PackageNameConstant.JOBHANDLER);
            } else {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_XXLJOB_CONFIG_JOBPACKAGE, enableXxlJob.jobPackage());
            }
            List<String> mapperPackageNames = new ArrayList<>();
            mapperPackageNames.add(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_XXLJOB_CONFIG_JOBPACKAGE));
            Class<?> jobClass = Class.forName("com.spring.simple.development.core.component.job.XxlJobConfig");
            Class jobConfig = ClassLoadUtil.javassistCompile(jobClass, "org.springframework.context.annotation.ComponentScan", mapperPackageNames, "basePackages");
            return jobConfig;
        } catch (Exception ex) {
            throw new RuntimeException("XxlJob Config initialization failed", ex);
        }
    }

}
