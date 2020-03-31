package com.spring.simple.development.core.component.alertsdk.handler;

import lombok.Data;

/**
 * @author liko.wang
 * @Date 2020/3/31/031 16:32
 * @Description 消息实体
 **/
@Data
public class MessageDto {
    /**
     * @author liko.wang
     * @Date 2020/3/31/031 20:08
     * @Description 应用Code
     **/
    private String applicationCode;
    /**
     * @author liko.wang
     * @Date 2020/3/31/031 20:09
     * @Description 应用token
     **/
    private String applicationToken;
    /**
     * @author liko.wang
     * @Date 2020/3/31/031 20:08
     * @Description 消息级别  1,一般消息。2，重要消息。3，特别重要消息
     **/
    private String messageLevel;
    /**
     * @author liko.wang
     * @Date 2020/3/31/031 20:09
     * @param null
     * @return
     * @Description 消息体
     **/
    private String message;

}