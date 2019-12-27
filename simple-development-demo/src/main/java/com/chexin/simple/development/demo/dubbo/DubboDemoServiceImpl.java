package com.spring.simple.development.demo.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.spring.simple.development.service.DubboDemoService;
import org.springframework.stereotype.Component;

@Component
@Service
public class DubboDemoServiceImpl implements DubboDemoService {
    @Override
    public String sayHello() {
        return null;
    }
}
