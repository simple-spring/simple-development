//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spring.simple.development.demo.interceptor;

import com.spring.simple.development.core.annotation.base.SimpleInterceptor;
import com.spring.simple.development.support.exception.LanguageThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author luke
 * @Date 2019年12月30日
 * @Description simple 拦截器
 */
@SimpleInterceptor
public class DemoInterceptor implements HandlerInterceptor {

    public DemoInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        LanguageThreadLocal.setLanguage("_EN");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        LanguageThreadLocal.removeLanguage();
    }
}
