package com.spring.simple.development.core.baseconfig.idempotent;

/**
 * @author liko
 * @Date 2019-09-16 10:44
 * @DESCRIPTION TODO
 */
public class IdempotentModel {
    /**
     * 请求地址
     */
    private String url;
    /**
     * ip地址
     */
    private String ip;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}