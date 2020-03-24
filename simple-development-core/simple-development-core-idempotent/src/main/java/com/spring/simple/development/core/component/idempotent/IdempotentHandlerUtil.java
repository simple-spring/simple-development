package com.spring.simple.development.core.component.idempotent;

import com.alibaba.fastjson.JSON;
import com.spring.simple.development.core.annotation.base.Idempotent;
import com.spring.simple.development.core.baseconfig.idempotent.IdempotentHandler;
import com.spring.simple.development.core.baseconfig.idempotent.IdempotentModel;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.JedisPoolUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;

import static com.spring.simple.development.support.exception.ResponseCode.RES_IDEMPOTENT_INVALID;

/**
 * redis组件
 *
 * @author liko wang
 */
public class IdempotentHandlerUtil {
    /**
     * @param point
     * @throws Exception 请求前置拦截
     */
    public void before(JoinPoint point) throws NoSuchMethodException {
        Jedis jedis = null;
        try {
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
                String data;
                if (point.getArgs().length == 0) {
                    data = idempotentModel.getRandomData();
                } else {
                    Object paramData = point.getArgs()[0];
                    data = DigestUtils.md5Hex(JSON.toJSONString(paramData));
                }
                // 加入分布式锁
                jedis = JedisPoolUtils.getJedis();
                String idempotentValue = jedis.get(idempotentModel.getUrl() + idempotentModel.getIp() + value + data);
                if (StringUtils.isEmpty(idempotentValue)) {
                    jedis.setex(idempotentModel.getUrl() + idempotentModel.getIp() + value + data, 60, idempotentModel.getUrl() + idempotentModel.getIp() + value);
                    return;
                }
                throw new GlobalException(RES_IDEMPOTENT_INVALID, "点击太快了,请稍后重试");
            }
        } finally {
            JedisPoolUtils.returnRes(jedis);
        }
    }

    public void after(JoinPoint point) throws NoSuchMethodException {
        Jedis jedis = null;
        try {
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
                String data;
                if (point.getArgs().length == 0) {
                    data = idempotentModel.getRandomData();
                } else {
                    Object paramData = point.getArgs()[0];
                    data = DigestUtils.md5Hex(JSON.toJSONString(paramData));
                }
                // 删除缓存
                jedis = JedisPoolUtils.getJedis();
                jedis.del(idempotentModel.getUrl() + idempotentModel.getIp() + value + data);
            }
        } finally {
            JedisPoolUtils.returnRes(jedis);
        }
    }
}
