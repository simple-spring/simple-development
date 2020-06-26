package com.spring.simple.development.demo.dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * @author liko
 * @Date 2020-03-23 15:26
 * @DESCRIPTION TODO
 */
@Service
public class DubboDemoServiceImpl implements DubboDemoService {

    @Reference
    private DubboDemoService dubboDemoService;

    @Override
    public String sayHello() {
        return null;
    }
}