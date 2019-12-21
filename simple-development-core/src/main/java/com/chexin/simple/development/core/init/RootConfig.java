package com.chexin.simple.development.core.init;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 15:24
 * @Description //TODO
 **/

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@Import({DruidDataSourceConfig.class,BaseConfig.class})
public class RootConfig {

}