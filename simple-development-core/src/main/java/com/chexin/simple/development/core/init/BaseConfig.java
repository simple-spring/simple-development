package com.chexin.simple.development.core.init;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author liko
 * @Date 2019-12-21 15:22
 * @DESCRIPTION TODO
 */
@Configuration
@ComponentScan(basePackages = {"com.chexin.simple.development.core.init","com.chexin.simple.development.core.handler", "com.chexin.simple.development.core.jdbc", "com.chexin.simple.development.core.Idempotent", "com.chexin.simple.development.core.valid", "com.chexin.simple.development.core.dozer", "com.chexin.simple.development.core.datasource"})
public class BaseConfig {
    public BaseConfig() {
        System.out.println("base package scan init");
    }

}