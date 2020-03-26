package com.spring.simple.development.core.baseconfig.user;

import com.alibaba.lava.privilege.PrivilegeInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户session
 *
 * @author liko wang
 */
public interface SimpleSessionProfile<T extends PrivilegeInfo> {

    /**
     * 获取用户信息
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @return
     */
    T getPrivilegeInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler);

    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @return java.lang.String
     * @author liko.wang
     * @Date 2020/3/26/026 16:27
     * @Description 用户登录
     **/
    String privilegeInfoLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);


    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @return java.lang.String
     * @author liko.wang
     * @Date 2020/3/26/026 16:27
     * @Description 用户注销
     **/
    void privilegeInfoLogout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
