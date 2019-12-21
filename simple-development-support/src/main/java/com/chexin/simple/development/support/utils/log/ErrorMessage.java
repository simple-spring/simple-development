package com.chexin.simple.development.support.utils.log;

import lombok.Data;

/**
 * @author liko
 * @Date 2019-11-20 9:47
 * @DESCRIPTION 错误日志收集
 */
@Data
public class ErrorMessage {
    // 日志Id
    private String logId;
    // 项目类型
    private String projectType;
    // 日志类型
    private String logType;
    // 服务器IP
    private String ip;
    // 请求地址
    private String url;
    // 调用者IP
    private String remoteIp;
    // log地址
    private String logPath;
    // 调用时间
    private String date;
    // 地址内容
    private String content;
    // 描述
    private String description;
}