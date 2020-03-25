package com.spring.simple.development.core.baseconfig.user;

import com.alibaba.lava.privilege.PrivilegeInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户session
 * @author liko wang
 */
public interface SimpleSessionProfile<T extends PrivilegeInfo> {

    /**
     * 获取用户信息
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @return
     */
    T  getPrivilegeInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler);
}
