package com.spring.simple.development.demo.kafka;

import com.spring.simple.development.core.annotation.base.SimpleTopic;
import com.spring.simple.development.core.component.kafka.service.SpringSimpleKafkaService;

/**
 * @author liko.wang
 * @Date 2020/4/3/003 9:08
 * @Description //TODO
 **/
@SimpleTopic(value = "message")
public class TestTopic implements SpringSimpleKafkaService {

    @Override
    public void getConsumerMessage(String message) {
        System.out.println(message);
    }
}