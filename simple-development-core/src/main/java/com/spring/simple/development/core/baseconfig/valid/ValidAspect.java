package com.spring.simple.development.core.baseconfig.valid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.baseconfig.isapiservice.SimpleService;
import com.spring.simple.development.core.component.mvc.req.ReqBody;
import com.spring.simple.development.support.exception.GlobalException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_INVALID;
import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;


/**
 * @author liko.wang
 * @Date 2019/12/18/018 15:30
 * @Description参数校验
 **/
@Order(value = 0)
@Aspect
@Component
public class ValidAspect extends SimpleService {

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
        Class<?> validUtilClass = Class.forName("com.spring.simple.development.core.component.valid.ValidUtil");
        Method around = validUtilClass.getMethod("around", joinPoint.getClass());
        Object result = around.invoke(validUtilClass.newInstance(), joinPoint);
        return result;
    }
}