package com.spring.simple.development.core.component.kafka.service;

/**
 * @desc: Kafka公共接口
 * @auth: hjs
 * @date: 2020/3/31
 */

public interface SpringSimpleKafkaService {

    /**
     * 获取消费端返回消息
     *
     * @param message 消息体
     */
    void getConsumerMessage(String message);

}
