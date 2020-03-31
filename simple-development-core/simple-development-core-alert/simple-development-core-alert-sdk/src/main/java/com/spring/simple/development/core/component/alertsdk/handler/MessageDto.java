package com.spring.simple.development.core.component.alertsdk.handler;

import lombok.Data;

/**
 * @author liko.wang
 * @Date 2020/3/31/031 16:32
 * @Description 消息实体
 **/
@Data
public class MessageDto {
    private String applicationCode;
    private String applicationToken;
    private String messageLevel;
    private String message;

}