package com.spring.simple.development.core.component.job;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;

/**
 * @author ys
 * @Date 2019年12月27日
 * @Description TODO
 */
@Configuration
public class XxlJobConfig {

	@Bean(initMethod = "start", destroyMethod = "destroy")
    public XxlJobSpringExecutor xxlJobExecutor() {
        System.out.println("xxl job initialized...");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_XXLJOB_CONFIG_ADDRESSES));
        xxlJobSpringExecutor.setAppName(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_XXLJOB_CONFIG_APPNAME));
        xxlJobSpringExecutor.setIp(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_XXLJOB_CONFIG_IP));
        xxlJobSpringExecutor.setPort(Integer.valueOf(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_XXLJOB_CONFIG_PORT)));
        xxlJobSpringExecutor.setAccessToken(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_XXLJOB_CONFIG_ACCESSTOKEN));
        xxlJobSpringExecutor.setLogPath(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_XXLJOB_CONFIG_LOGPATH));
        xxlJobSpringExecutor.setLogRetentionDays(Integer.valueOf(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_XXLJOB_CONFIG_LOGRETENTIONDAYS)));
        return xxlJobSpringExecutor;
    }
}
