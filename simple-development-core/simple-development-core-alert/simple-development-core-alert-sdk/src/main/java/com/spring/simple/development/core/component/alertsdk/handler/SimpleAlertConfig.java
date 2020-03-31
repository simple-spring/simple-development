package com.spring.simple.development.core.component.alertsdk.handler;

import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.springframework.context.annotation.Bean;

/**
 * @author liko.wang
 * @Date 2020/3/31/031 15:25
 * @Description 执行器
 **/
public class SimpleAlertConfig {

    private static String applicationCode = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_ALERT_CONFIG_APPLICATION_CODE);
    private static String applicationToken = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_ALERT_CONFIG_APPLICATION_TOKEN);
    private static String alertCollectionUrl = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_ALERT_CONFIG_COLLECTION_URL);
    private static String localLogFile = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_ALERT_CONFIG_APPLICATION_LOG_PATH);

    private static AlertThread alertThread;

    @Bean(initMethod = "init", destroyMethod = "close")
    public SimpleAlertConfig getSimpleAlertExecutor() {
        return new SimpleAlertConfig();
    }

    // 初始化操作
    public void init() {
        // 启动收集线程
        alertThread = new AlertThread(applicationToken, applicationCode, alertCollectionUrl, localLogFile);
        alertThread.start();
    }

    // 容器关闭操作
    public void close() throws InterruptedException {
        alertThread.toStop();
    }

    // 添加消息
    public static void putMessage(String message, String messageLevel) {
        MessageDto messageDto = new MessageDto();
        messageDto.setApplicationCode(applicationCode);
        messageDto.setApplicationToken(applicationToken);
        messageDto.setMessage(message);
        messageDto.setMessageLevel(messageLevel);
        alertThread.joinQueue(message);
    }
}
