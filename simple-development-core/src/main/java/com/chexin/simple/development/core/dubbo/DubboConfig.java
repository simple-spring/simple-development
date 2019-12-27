package com.chexin.simple.development.core.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.chexin.simple.development.core.constant.SystemProperties;
import com.chexin.simple.development.support.properties.PropertyConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConfig {
    /**
     * @author liko.wang
     * @Date 2019/12/27/027 16:09
     * @Description 应用名
     **/
    private String applicationName = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_APPLICATION_NAME);
    /**
     * @author liko.wang
     * @Date 2019/12/27/027 16:09
     * @Description 注册地址
     **/
    private String address = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_REGISTRY_ADDRESS);
    /**
     * @author liko.wang
     * @Date 2019/12/27/027 16:09
     * @Description 协议名
     **/
    private String protocolName = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_PROTOCOL_NAME);
    /**
     * @author liko.wang
     * @Date 2019/12/27/027 16:09
     * @Description 端口号
     **/
    private Integer port = Long.valueOf(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_DUBBO_CONFIG_PROTOCOL_PORT)).intValue();

    /**
     * 应用名
     *
     * @return
     */
    @Bean
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName(applicationName);
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
        registryConfig.setAddress(address);
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
        protocolConfig.setName(protocolName);
        protocolConfig.setPort(port);
        return protocolConfig;
    }
}
