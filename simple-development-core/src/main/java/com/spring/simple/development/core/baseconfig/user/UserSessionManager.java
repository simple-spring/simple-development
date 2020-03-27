package com.spring.simple.development.core.baseconfig.user;

import com.alibaba.lava.privilege.PrivilegeInfo;

/**
 * @author liko.wang
 * @Date 2020/3/27/027 9:49
 * @Description 用户session管理
 **/
public class UserSessionManager<T> {
    private static final ThreadLocal<Class> LOCALUSERSESSION = new ThreadLocal<>();

    public static Class getPrivilegeInfo() {
        return LOCALUSERSESSION.get() == null ? null : (Class<? super PrivilegeInfo>) LOCALUSERSESSION.get();
    }

    public static void setPrivilegeInfo(Class privilegeInfo) {
        LOCALUSERSESSION.set(privilegeInfo);
    }

    public static void clear() {
        LOCALUSERSESSION.remove();
    }
}