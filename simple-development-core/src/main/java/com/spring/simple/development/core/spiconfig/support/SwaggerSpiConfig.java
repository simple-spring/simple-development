package com.spring.simple.development.core.spiconfig.support;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.annotation.config.EnableSwagger;
import com.spring.simple.development.core.annotation.config.EnableXxlJob;
import com.spring.simple.development.core.component.job.XxlJobConfig;
import com.spring.simple.development.core.component.swagger.SwaggerConfig;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;
import com.spring.simple.development.support.constant.PackageNameConstant;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.spring.simple.development.support.utils.ClassLoadUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author liko
 * @Date 2019年12月27日
 * @Description EnableSwagger
 */
@Spi(configName = "EnableSwagger")
public class SwaggerSpiConfig implements SimpleSpiConfig<EnableSwagger, SwaggerConfig> {

    @Override
    public Class<SwaggerConfig> getConfigClass(EnableSwagger enableSwagger) {
        try {
            PropertyConfigurer.setProperty(SystemProperties.APPLICATION_SWAGGER_TITLE, "spring simple swagger doc");
            PropertyConfigurer.setProperty(SystemProperties.APPLICATION_SWAGGER_DESCRIPTION, "基于ssm javaconfig 零xml配置，常用的三层结构dao，service简化开发,controller层零开发,以组件方式自由搭配，简单灵活");
            PropertyConfigurer.setProperty(SystemProperties.APPLICATION_SWAGGER_VERSION, "1.0");
            PropertyConfigurer.setProperty(SystemProperties.APPLICATION_SWAGGER_URL, "https://swagger.io/");
            PropertyConfigurer.setProperty(SystemProperties.APPLICATION_SWAGGER_CONTACT, "spring simple team");

            if (!StringUtils.isEmpty(enableSwagger.title())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_SWAGGER_TITLE, enableSwagger.title());
            }
            if (!StringUtils.isEmpty(enableSwagger.description())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_SWAGGER_DESCRIPTION, enableSwagger.description());
            }
            if (!StringUtils.isEmpty(enableSwagger.version())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_SWAGGER_VERSION, enableSwagger.version());
            }
            if (!StringUtils.isEmpty(enableSwagger.url())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_SWAGGER_URL, enableSwagger.url());
            }
            if (!StringUtils.isEmpty(enableSwagger.contact())) {
                PropertyConfigurer.setProperty(SystemProperties.APPLICATION_SWAGGER_CONTACT, enableSwagger.contact());
            }
            return SwaggerConfig.class;
        } catch (Exception ex) {
            throw new RuntimeException("swagger Config initialization failed", ex);
        }
    }

}
