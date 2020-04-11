package com.spring.simple.development.core.component.shiro.cas;

import com.acl.support.auth.web.authc.Account;
import com.acl.support.auth.web.util.AccountUtils;
import com.alibaba.lava.privilege.PrivilegeInfo;
import com.spring.simple.development.core.baseconfig.context.SimpleApplication;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 描述:
 * shiro-lava支持
 *
 * @author liko
 * @create 2018-10-09 上午9:13
 */
public class ShiroLavaSupportInterceptor implements HandlerInterceptor {
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
        Subject subject = SecurityUtils.getSubject();
        Object principal = subject.getPrincipal();
        if (principal == null) {
            return true;
        }
        // 赋值
        Account currentAccount = AccountUtils.getCurrentAccount();
        if (currentAccount == null) {
            return true;
        }
        // 获取用户对象
        PrivilegeInfo sessionPrivilegeInfo = SimpleApplication.getBeanByType(PrivilegeInfo.class);
        // 通过
        sessionPrivilegeInfo.setUserId(currentAccount.getId());
        sessionPrivilegeInfo.setOpenAccount(currentAccount.getAccountCode());
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
        // 获取用户对象
        PrivilegeInfo sessionPrivilegeInfo = SimpleApplication.getBeanByType(PrivilegeInfo.class);
        // 通过
        sessionPrivilegeInfo.setUserId(null);
        sessionPrivilegeInfo.setOpenAccount(null);
    }
}