package com.chexin.simple.development.core.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.chexin.simple.development.support.properties.PropertyConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConfig {
    /**
     * 应用名
     *
     * @return
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(PropertyConfigurer.getProperty("dubbo.application.name"));
        return applicationConfig;
    }

    /**
     * <dubbo:provider timeout="10000" />
     *
     * @return
     */
    @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(10000);
        return providerConfig;
    }


    /**
     * 地址配置 <dubbo:registry address="zookeeper://127.0.0.1:2181" client="zkclient"/>
     *
     * @return
     */
    @Bean
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress(PropertyConfigurer.getProperty("dubbo.registry.address"));
        registryConfig.setClient("zkclient");
        return registryConfig;
    }


    /**
     * 协议配置，等同于 <dubbo:protocol name="dubbo" port="20880" />
     *
     * @return ProtocolConfig
     */
    @Bean
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName(PropertyConfigurer.getProperty("dubbo.protocol.name"));
        protocolConfig.setPort(Long.valueOf(PropertyConfigurer.getProperty("dubbo.protocol.port")).intValue());
        return protocolConfig;
    }
}
