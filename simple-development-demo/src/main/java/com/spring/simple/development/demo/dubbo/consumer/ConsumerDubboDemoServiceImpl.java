package com.spring.simple.development.demo.dubbo.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.spring.simple.development.demo.dubbo.provider.ProviderDubboDemoService;
import org.springframework.stereotype.Service;

@Service
public class ConsumerDubboDemoServiceImpl implements ConsumerDubboDemoService {

    @Reference
    private ProviderDubboDemoService providerDubboDemoService;

    @Override
    public void consumerMethod() {
        System.out.println(providerDubboDemoService.sayHello());
    }
}
