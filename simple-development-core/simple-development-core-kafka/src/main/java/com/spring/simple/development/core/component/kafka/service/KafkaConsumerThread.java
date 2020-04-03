package com.spring.simple.development.core.component.kafka.service;

import com.spring.simple.development.support.constant.SystemProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Properties;

/**
 * @desc: kafka消费线程
 * @auth: hjs
 * @date: 2020/4/1
 */
public class KafkaConsumerThread implements Runnable {

    private static final Logger logger = LogManager.getLogger(KafkaConsumerThread.class);

    private String topic;

    private Class configClass;

    private Properties props;

    public KafkaConsumerThread(String topic, Class configClass, Properties props) {
        this.topic = topic;
        this.configClass = configClass;
        this.props = props;
    }

    @Override
    public void run() {
        try {
            Object configObject = configClass.newInstance();
            Method method = configClass.getDeclaredMethod(SystemProperties.KAFKA_GET_CONSUMER_METHOD_NAME, String.class);
            @SuppressWarnings("resource")
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            consumer.subscribe(Arrays.asList(topic));
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                for (ConsumerRecord<String, String> record : records) {
                    logger.info("topic:" + topic + ",offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                    // 不是json格式跳过处理
                    String message = record.value();
                    if (StringUtils.isEmpty(message) || !message.contains("{")) {
                        continue;
                    }
                    message = message.substring(message.indexOf("{"));
                    method.invoke(configObject, message);
                }
            }
        } catch (Exception e) {
            System.out.println("KafkaConsumerThread Consumer failed");
            throw new RuntimeException(e);
        }
    }
}
