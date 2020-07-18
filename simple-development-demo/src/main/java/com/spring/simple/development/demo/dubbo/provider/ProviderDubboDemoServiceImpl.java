package com.spring.simple.development.demo.dubbo.provider;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author liko
 * @Date 2020-03-23 15:26
 * @DESCRIPTION 提供服务
 */
@Service
public class ProviderDubboDemoServiceImpl implements ProviderDubboDemoService {

    @Override
    public String sayHello() {
        return "hello spring simple";
    }
}