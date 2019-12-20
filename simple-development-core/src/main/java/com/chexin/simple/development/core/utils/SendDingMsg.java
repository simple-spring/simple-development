package com.chexin.simple.development.core.utils;


import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.chatbot.DingtalkChatbotClient;
import com.dingtalk.chatbot.message.TextMessage;
import com.chexin.simple.development.core.log.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * desc:    发送钉钉群工具类
 */
public class SendDingMsg {
    /**
     * 钉钉群自定义机器人> 系统异常机器人
     */
    public static final String DINGTALK_MSG_URL = "https://oapi.dingtalk.com/robot/send?access_token=008643a00da8ffdca96a571a924217d8c25a15e03bd358c7decf1fb481de1aef";
    public static final String DINGTALK_PESOGRAB_MSG_URL = "https://oapi.dingtalk.com/robot/send?access_token=8f761ab7b964161e39c31211f29c58c801a9be735c93033a6a97410e226168c0";
    private static DingtalkChatbotClient client = new DingtalkChatbotClient();

    /**
     * 发送文本消息
     *
     * @param content
     * @return
     * @throws Exception
     */
    public static void sendTextMessage(String content) {
        try {
            TextMessage message = new TextMessage(content + "服务器ip是[" + IpUtil.getLocalhostIp() + "]" + " 时间:" + DateUtils.getCurrentTime());
            client.send(DINGTALK_MSG_URL, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送文本消息 ,@群里的指定用户
     *
     * @param content
     * @param atMobile 用户手机号码.
     * @return
     * @throws Exception
     */
    public static void sendTextMessageWithOne(String content, String atMobile) {
        try {
            TextMessage message = new TextMessage(content + "服务器ip是[" + IpUtil.getLocalhostIp() + "]");
            List<String> atMobiles = new ArrayList<>();
            atMobiles.add(atMobile);
            message.setAtMobiles(atMobiles);
            client.send(DINGTALK_MSG_URL, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送文本消息 ,@群里的指定多个用户
     *
     * @param content
     * @param atMobiles 用户手机号码.
     * @return
     * @throws Exception
     */
    public static void sendTextMessageWithing(String content, List<String> atMobiles) {
        try {
            TextMessage message = new TextMessage(content + "服务器ip是[" + IpUtil.getLocalhostIp() + "]");
            message.setAtMobiles(atMobiles);
            client.send(DINGTALK_MSG_URL, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * link方式通知
     *
     * @param title
     * @param message
     * @param url
     * @throws Exception
     */
    public static void sendlinkMsg(String title, String message, String url) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(DINGTALK_MSG_URL);
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype("link");
            OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
            link.setTitle(title);
            link.setMessageUrl(url);
            link.setPicUrl("http://file.diangc.cn/d3b1dd4a69ac4323a7d22ead627b548bc5fb5b27c6cd48d8ede9f6f7d2e6a5d1.jpg");
            link.setText("" + message + "\n" + "服务器ip: " + IpUtil.getLocalhostIp() + "\n" + "时间:" + DateUtils.getCurrentTime());
            request.setLink(link);
            client.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMarkdown(ErrorMessage errorMessage) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(DINGTALK_MSG_URL);
            OapiRobotSendRequest request = new OapiRobotSendRequest();

            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle("平台日志报错");
            markdown.setText("## 项目" + errorMessage.getProjectType() + "错误提醒\n" +
                    "**日志来源**\n" +
                    "   >  " + errorMessage.getLogType() + "\n" +
                    "### 日志Id\n" +
                    "   >  " + errorMessage.getLogId() + "\n" +
                    "#### 服务器Ip\n" +
                    "   >  " + errorMessage.getIp() + "\n" +
                    "### 日志路径\n" +
                    "   >  " + errorMessage.getLogPath() + "\n" +
                    "### 时间\n" +
                    "   >  " + errorMessage.getDate() + "\n" +
                    "### 描述\n" +
                    "   >  " + errorMessage.getDescription() + "\n" +
                    "### 请求地址\n" +
                    "   >  " + errorMessage.getUrl() + "\n" +
                    "### 请求者IP\n" +
                    "   >  " + errorMessage.getRemoteIp() + "\n" +
                    "### 日志内容\n" +
                    "   >  [日志内容地址](http://kibana.pesograb.com/app/kibana)");
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setIsAtAll("true");
            request.setAt(at);
            request.setMsgtype("markdown");
            request.setMarkdown(markdown);
            client.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void sendPesoGrabMarkdown(String title,String content) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(DINGTALK_PESOGRAB_MSG_URL);
            OapiRobotSendRequest request = new OapiRobotSendRequest();

            OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
            markdown.setTitle("pesograb消息提醒");
            markdown.setText("## pesograb消息提醒\n" +
                    "**标题**\n" +
                    "   >  " + title + "\n" +
                    "### 内容\n" +
                    "   >  " +content + "\n");
            request.setMsgtype("markdown");
            request.setMarkdown(markdown);
            client.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}