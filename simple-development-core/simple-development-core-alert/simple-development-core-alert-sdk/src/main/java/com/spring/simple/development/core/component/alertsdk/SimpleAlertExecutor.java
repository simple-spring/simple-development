package com.spring.simple.development.core.component.alertsdk;

import com.spring.simple.development.core.component.alertsdk.handler.SimpleAlertConfig;
import com.spring.simple.development.support.utils.DateUtils;
import com.spring.simple.development.support.utils.IpUtil;
import com.spring.simple.development.support.utils.PrimaryKeyGenerator;
import com.spring.simple.development.support.utils.log.ErrorMessage;

import java.io.PrintWriter;
import java.io.StringWriter;

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

    /**
     * @param description
     * @param e
     * @return void
     * @author liko.wang
     * @Date 2020/3/31/031 16:28
     * @Description 发送一般消息
     **/
    public static void sendThrowableLowMessage(String description, Throwable e) {
        ErrorMessage errorMessage = getErrorMessage(description, e);
        SimpleAlertConfig.putMessage(errorMessage.toString(), lowMessage);
    }

    private static ErrorMessage getErrorMessage(String description, Throwable e) {
        // 日志转转
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        String errorMsg = stringWriter.toString();
        // 记录日志内容
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setLogId(PrimaryKeyGenerator.getInstance().nextId().toString());
        errorMessage.setProjectType("api");
        errorMessage.setLogType("restful api");
        errorMessage.setIp(IpUtil.getIp());
        errorMessage.setLogPath("/data/logs/simple-development-core/error/error.log");
        errorMessage.setDate(DateUtils.getCurrentTime());
        errorMessage.setContent(errorMsg);
        errorMessage.setDescription(description);
        errorMessage.setUrl("");
        errorMessage.setRemoteIp("");
        return errorMessage;
    }

    /**
     * @param description
     * @param e
     * @return void
     * @author liko.wang
     * @Date 2020/3/31/031 16:28
     * @Description 发送重要消息
     **/
    public static void sendThrowableMediumMessage(String description, Throwable e) {
        ErrorMessage errorMessage = getErrorMessage(description, e);
        SimpleAlertConfig.putMessage(errorMessage.toString(), mediumMessage);
    }

    /**
     * @param description
     * @param e
     * @return void
     * @author liko.wang
     * @Date 2020/3/31/031 16:29
     * @Description 发送特别重要消息
     **/
    public static void sendThrowableHighMessage(String description, Throwable e) {
        ErrorMessage errorMessage = getErrorMessage(description, e);
        SimpleAlertConfig.putMessage(errorMessage.toString(), highMessage);
    }

}