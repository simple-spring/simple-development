package com.spring.simple.development.core.baseconfig.valid;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * @author liko.wang
 * @Date 2019/12/18/018 15:30
 * @Description参数校验
 **/
@Order(value = 0)
@Aspect
@Component
public class ValidAspect {

    @Pointcut("@annotation(com.spring.simple.development.core.annotation.base.ValidHandler)")
    public void annotationPointcut() {
    }

    /**
     * 获取参数进行入参校验
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("annotationPointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        return ValidUtil.around(joinPoint);
    }
}