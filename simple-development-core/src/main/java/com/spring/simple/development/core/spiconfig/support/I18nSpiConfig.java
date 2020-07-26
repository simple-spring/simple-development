package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.ImportI18n;
import com.spring.simple.development.core.baseconfig.i18n.I18nConfig;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;

/**
 * @author liko wang
 */
@Spi(configName = "ImportI18n")
public class I18nSpiConfig implements SimpleSpiConfig<ImportI18n> {
    @Override
    public Class getConfigClass(ImportI18n importI18n) {
        try {
            String[] strings = importI18n.i18nPath();
            StringBuffer stringBuffer = new StringBuffer();
            for (String path : strings) {
                stringBuffer.append(path + ",");
            }
            stringBuffer.substring(0, stringBuffer.length() - 1);
            PropertyConfigurer.setProperty(SystemProperties.SPRING_SIMPLE_I18N_PATH, stringBuffer.toString());
            return I18nConfig.class;
        } catch (Exception ex) {
            throw new RuntimeException("ImportI18n initialization failed", ex);
        }
    }
}
