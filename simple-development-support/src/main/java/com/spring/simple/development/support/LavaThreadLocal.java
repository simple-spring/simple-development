package com.spring.simple.development.support;

import com.alibaba.lava.privilege.PrivilegeInfo;

/**
 * lava用户信息保存
 *
 * @author luke
 */
public class LavaThreadLocal {
    public static ThreadLocal<PrivilegeInfo> privilegeInfoThreadLocal = new ThreadLocal<>();

    public static PrivilegeInfo getLava() {
        return privilegeInfoThreadLocal.get();
    }

    public static void remove() {
        privilegeInfoThreadLocal.remove();
    }

    public static void addLava(PrivilegeInfo privilegeInfo) {
        privilegeInfoThreadLocal.set(privilegeInfo);
    }
}
