package com.chexin.simple.development.core.handler;

import com.chexin.simple.development.core.mvc.controller.SignInfo;

/**
 * 描述:
 * app sign用户管理器
 *
 * @author liko
 * @create 2018-10-15 下午11:44
 */
public class ThreadLocalSignInfoHandler {
    static ThreadLocal<SignInfo> signInfoDoThreadLocal = new ThreadLocal<>();

    public static void setSignInfo(SignInfo signInfo) {
        signInfoDoThreadLocal.set(signInfo);
    }

    public static SignInfo getSignInfo() {
        return signInfoDoThreadLocal.get();
    }

    public static void remove() {
        signInfoDoThreadLocal.remove();
    }
}