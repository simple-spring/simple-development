package com.spring.simple.development.core.init;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author liko
 * @Date 2019-12-22 10:54
 * @DESCRIPTION 基础配置
 */
@Configuration
@ComponentScan(basePackages = {"com.spring.simple.development.core.init", "com.spring.simple.development.core.handler", "com.spring.simple.development.core.Idempotent", "com.spring.simple.development.core.valid", "com.spring.simple.development.core.dozer"})
public class BaseConfig {
    public BaseConfig() {
        System.out.println("base config init ");
    }
}