package com.chexin.simple.development.demo.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.chexin.simple.development.service.DubboDemoService;
import org.springframework.stereotype.Component;

@Component
@Service
public class DubboDemoServiceImpl implements DubboDemoService {
    @Override
    public String sayHello() {
        return null;
    }
}
