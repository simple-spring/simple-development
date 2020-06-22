package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableAlert;
import com.spring.simple.development.core.annotation.config.EnableDataProcess;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liko wang
 */
@Spi(configName = "EnableDataProcess")
public class DataProcessSpiConfig implements SimpleSpiConfig<EnableDataProcess> {
    @Override
    public Class getConfigClass(EnableDataProcess enableDataProcess) {
        try {

            Class<?> targetClass = Class.forName("com.spring.simple.development.core.component.data.process.DataProcessConfig");
            return targetClass;
        } catch (Exception ex) {
            throw new RuntimeException("DataProcessConfig initialization failed", ex);
        }
    }
}
