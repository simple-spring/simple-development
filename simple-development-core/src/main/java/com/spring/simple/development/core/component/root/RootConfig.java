package com.spring.simple.development.core.component.root;

/**
 * @author liko.wang  todo 动态选配
 * @Date 2019/12/19/019 15:24
 * @Description 启动spring入口
 **/

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class RootConfig {
    public RootConfig() {
        System.out.println("spring simple root component init");
    }
}