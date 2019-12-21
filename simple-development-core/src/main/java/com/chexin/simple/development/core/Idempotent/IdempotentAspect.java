package com.chexin.simple.development.core.Idempotent;

import com.alibaba.fastjson.JSON;
import com.chexin.simple.development.core.annotation.Idempotent;
import com.chexin.simple.development.support.utils.JedisPoolUtils;
import com.chexin.simple.development.support.GlobalException;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;

import static com.chexin.simple.development.support.ResponseCode.RES_IDEMPOTENT_INVALID;


/**
 * 拦截接口幂等目标方法
 *
 * @author
 */
@Order(value = 1)
@Aspect
@Component
public class IdempotentAspect {
    @Pointcut("@annotation(com.chexin.simple.development.core.annotation.Idempotent)")
    public void annotationPointcut() {
    }

    /**
     * @param point
     * @throws Exception 请求前置拦截
     */
    @Before("annotationPointcut()")
    public void before(JoinPoint point) throws Exception {
        // 获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        // 获得访问的方法名
        String methodName = point.getSignature().getName();
        // 得到方法的参数的类型
        Class<?>[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
        // 得到访问的方法对象
        Method method = className.getMethod(methodName, argClass);
        method.setAccessible(true);
        // 判断是否存在@Valid注解
        if (method.isAnnotationPresent(Idempotent.class)) {
            Idempotent idempotent = method.getAnnotation(Idempotent.class);
            String value = idempotent.value();
            IdempotentModel idempotentModel = IdempotentHandler.getIdempotentModel();
            Object paramData = point.getArgs()[0];
            String data = DigestUtils.md5Hex(JSON.toJSONString(paramData));
            Jedis jedis = JedisPoolUtils.getJedis();
            String idempotentValue = jedis.get(idempotentModel.getUrl() + idempotentModel.getIp() + value + data);
            if (StringUtils.isEmpty(idempotentValue)) {
                jedis.setex(idempotentModel.getUrl() + idempotentModel.getIp() + value + data, 10, idempotentModel.getUrl() + idempotentModel.getIp() + value);
                return;
            }
            throw new GlobalException(RES_IDEMPOTENT_INVALID, "点击太快了,请稍后重试");
        }
    }

    @After("annotationPointcut()")
    public void after(JoinPoint point) throws Exception {
        // 获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        // 获得访问的方法名
        String methodName = point.getSignature().getName();
        // 得到方法的参数的类型
        Class<?>[] argClass = ((MethodSignature) point.getSignature()).getParameterTypes();
        // 得到访问的方法对象
        Method method = className.getMethod(methodName, argClass);
        method.setAccessible(true);
        // 判断是否存在@Valid注解
        if (method.isAnnotationPresent(Idempotent.class)) {
            IdempotentModel idempotentModel = IdempotentHandler.getIdempotentModel();
            Idempotent idempotent = method.getAnnotation(Idempotent.class);
            String value = idempotent.value();
            Object paramData = point.getArgs()[0];
            String data = DigestUtils.md5Hex(JSON.toJSONString(paramData));
            Jedis jedis = JedisPoolUtils.getJedis();
            jedis.del(idempotentModel.getUrl() + idempotentModel.getIp() + value + data);
        }
    }
}
