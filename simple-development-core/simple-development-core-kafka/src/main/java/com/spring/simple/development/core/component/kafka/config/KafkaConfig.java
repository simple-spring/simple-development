package com.spring.simple.development.core.component.kafka.config;

import com.spring.simple.development.core.annotation.base.SimpleTopic;
import com.spring.simple.development.core.component.kafka.service.KafkaConsumerThread;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.reflections.Reflections;
import org.springframework.util.CollectionUtils;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @desc: kafka组件
 * @auth: hjs
 * @date: 2020/3/31
 */
public class KafkaConfig {

    /**
     * 线程池
     */
    private static ExecutorService executorService = new ThreadPoolExecutor(2, 50, 1000, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    private KafkaConfig() {
        init();
    }

    /**
     * 初始化，消费数据
     */
    public void init() {
        Reflections sipReflections = new Reflections(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_KAFKA_CONFIG_PACKAGE));
        Set<Class<?>> classes = sipReflections.getTypesAnnotatedWith(SimpleTopic.class);
        if (CollectionUtils.isEmpty(classes)) {
            throw new RuntimeException("spring simple KafkaConfig component is empty");
        }
        // 通过spi找到组件
        for (Class configClass : classes) {
            SimpleTopic simpleTopic = (SimpleTopic) configClass.getAnnotation(SimpleTopic.class);
            executorService.submit(new KafkaConsumerThread(simpleTopic.value(), configClass, getProps()) {
            });
        }
    }

    /**
     * 消费端基础配置
     *
     * @return
     */
    public Properties getProps() {
        Properties props = new Properties();
        // 消费者需要连接的服务器集群地址
        props.put("bootstrap.servers", PropertyConfigurer.getProperty(SystemProperties.APPLICATION_KAFKA_BOOTSTRAP_SERVERS));
        // 消费组ID
        props.put("group.id", "test");
        // 是否开启自动提交offset
        props.put("enable.auto.commit", "true");
        // 配置项配置了每次自动提交的时间间隔：毫秒
        props.put("auto.commit.interval.ms", "1000");
        // 从最早的offset开始拉取，latest:从最近的offset开始消费
        props.put("auto.offset.reset", "earliest");
        // 每次批量拉取条数
        props.put("max.poll.records", "1000");
        // 指用什么方式进行反序列化
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }
}


