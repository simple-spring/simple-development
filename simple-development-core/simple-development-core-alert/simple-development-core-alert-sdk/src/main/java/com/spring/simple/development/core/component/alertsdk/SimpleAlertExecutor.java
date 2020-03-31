package com.spring.simple.development.core.component.alertsdk;

import com.spring.simple.development.core.component.alertsdk.handler.SimpleAlertConfig;

/**
 * @author liko.wang
 * @Date 2020/3/31/031 16:50
 * @Description //TODO
 **/
public class SimpleAlertExecutor {

    private static String lowMessage = "1";
    private static String mediumMessage = "2";
    private static String highMessage = "3";

    /**
     * @param message
     * @return void
     * @author liko.wang
     * @Date 2020/3/31/031 16:28
     * @Description 发送一般消息
     **/
    public static void sendLowMessage(String message) {
        SimpleAlertConfig.putMessage(message, lowMessage);
    }

    /**
     * @param message
     * @return void
     * @author liko.wang
     * @Date 2020/3/31/031 16:28
     * @Description 发送重要消息
     **/
    public static void sendMediumMessage(String message) {
        SimpleAlertConfig.putMessage(message, mediumMessage);
    }

    /**
     * @param message
     * @return void
     * @author liko.wang
     * @Date 2020/3/31/031 16:29
     * @Description 发送特别重要消息
     **/
    public static void sendHighMessage(String message) {
        SimpleAlertConfig.putMessage(message, highMessage);
    }

}