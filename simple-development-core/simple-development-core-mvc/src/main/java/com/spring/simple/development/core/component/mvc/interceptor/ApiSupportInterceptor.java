package com.spring.simple.development.core.component.mvc.interceptor;

import com.alibaba.lava.privilege.PrivilegeInfo;
import com.spring.simple.development.core.annotation.base.NoLogin;
import com.spring.simple.development.core.baseconfig.idempotent.IdempotentHandler;
import com.spring.simple.development.core.baseconfig.user.SimpleSessionProfile;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.core.baseconfig.context.SimpleContentApplication;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.GlobalResponseCode;
import com.spring.simple.development.support.exception.ResponseCode;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 描述:
 * 权限控制
 *
 * @author liko
 * @create 2018-10-09 上午9:13
 */
public class ApiSupportInterceptor implements HandlerInterceptor {
    /**
     * 该方法会在控制器方法前执行，其返回值表示是否中断后续操作。当其返回值为true时，表示继续向下执行
     * 当其返回值为false时，会中断后续的所有操作（包括调用下一个拦截器和控制器类中的方法执行等）
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        IdempotentHandler.fastSetIdempotentModel(httpServletRequest.getRemoteHost(), httpServletRequest.getRequestURI());

        String isEnable = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_USER_LOGIN_IS_OPEN);
        boolean isEnableBoolean = Boolean.parseBoolean(isEnable);
        if(isEnableBoolean == false) {
            return true;
        }
        // 不需要登录的地址
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            NoLogin noLogin = method.getMethodAnnotation(NoLogin.class);
            if (noLogin != null) {
                return true;
            }
        }
        // 获取已实现的接口
        if (SimpleContentApplication.isExistBean("simpleSessionProfile") == false) {
            // 未实现接口
            return true;
        }
        SimpleSessionProfile simpleSessionProfile = SimpleContentApplication.getBeanByType(SimpleSessionProfile.class);
        if (simpleSessionProfile == null) {
            // 未实现接口
            return true;
        }
        // 获取用户
        PrivilegeInfo privilegeInfo = simpleSessionProfile.getPrivilegeInfo(httpServletRequest, httpServletResponse, handler);
        if (privilegeInfo == null) {
            // 用户为空
            throw new GlobalException(GlobalResponseCode.SYS_NO_LOGIN, "用戶不存在或未登录");
        }
        // 获取用户对象
        PrivilegeInfo sessionPrivilegeInfo = SimpleContentApplication.getBeanByType(PrivilegeInfo.class);
        // 赋值
        sessionPrivilegeInfo.setOpenAccount(privilegeInfo.getOpenAccount());
        sessionPrivilegeInfo.setToken(privilegeInfo.getToken());
        // 通过
        return true;
    }

    /**
     * 该方法会在控制器方法调用之后，且解析视图之前执行。可以通过此方法对请求域中的模型和视图做出进一步的修改
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 该方法会在整个请求完成，即视图渲染结束之后执行。可以通过此方法实现一些资源清理、记录日志信息等工作。
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param handler
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        IdempotentHandler.clearIdempotentModel();
    }
}