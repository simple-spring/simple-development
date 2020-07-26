package com.spring.simple.development.core.baseconfig.i18n;

import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.exception.I18nConfigurer;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * @author luke
 */
@Configuration
public class I18nConfig {
    public I18nConfig() {
        String i18nPath = PropertyConfigurer.getProperty(SystemProperties.SPRING_SIMPLE_I18N_PATH);
        String[] paths = i18nPath.split(",");
        for (String path : paths) {
            I18nConfigurer.loadI18nProperties(path);
        }
    }
}
