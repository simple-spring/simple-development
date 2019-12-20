package com.chexin.simple.development.core.mvc.controller;


import com.chexin.simple.development.core.properties.PropertyConfigurer;

/**
 * @author liko
 * @Date 2019-09-27 13:44
 * @DESCRIPTION 签名校验
 */
public class SignInfo {

    public static final String ENCRYPT_SIGN_VALUE = PropertyConfigurer.getProperty("ENCRYPT_SIGN_VALUE");
    /**
     * 客户端版本号
     */
    private String versionCode;
    /**
     * 客户端平台1：1.android 2.ios
     */
    private String platform;
    /**
     * 设备号：Android：imei ios:idfv
     */
    private String deviceCode;
    /**
     * 用户token
     */
    private String token;
    /**
     * 加密串(versionCode+platform+deviceCode 2次MD5加密+fLWV4UhQ9a73xVVH)
     */
    private String encryptSign;

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEncryptSign() {
        return encryptSign;
    }

    public void setEncryptSign(String encryptSign) {
        this.encryptSign = encryptSign;
    }
}