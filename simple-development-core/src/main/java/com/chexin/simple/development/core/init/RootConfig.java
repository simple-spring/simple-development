package com.chexin.simple.development.core.init;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 15:24
 * @Description 启动spring入口
 **/

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy
@Import(BaseConfig.class)
public class RootConfig {
    public RootConfig() {
        System.out.println("Root config init");
    }
}