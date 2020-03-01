package com.spring.simple.development.core.component.valid;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.component.mvc.req.ReqBody;
import com.spring.simple.development.support.exception.GlobalException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Map;

import static com.spring.simple.development.support.exception.ResponseCode.*;

/**
 * redis组件
 *
 * @author liko wang
 */
public class ValidUtil {
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获得当前访问的class
        Class<?> className = joinPoint.getTarget().getClass();
        // 获得访问的方法名
        String methodName = joinPoint.getSignature().getName();
        // 得到方法的参数的类型
        Class<?>[] argClass = ((MethodSignature) joinPoint.getSignature()).getParameterTypes();
        // 得到访问的方法对象
        Method method = className.getMethod(methodName, argClass);
        method.setAccessible(true);
        // 判断是否存在@Valid注解
        if (method.isAnnotationPresent(ValidHandler.class)) {
            ValidHandler validHandler = method.getAnnotation(ValidHandler.class);

            if (validHandler.isReqBody()) {
                String[] keys = validHandler.key();
                Class<?>[] values = validHandler.value();
                if (keys.length != values.length) {
                    throw new GlobalException(RES_PARAM_INVALID, "valid参数无效");
                }
                boolean page = validHandler.isPage();
                Object[] args = joinPoint.getArgs();
                ReqBody reqBody = (ReqBody) args[0];
                // 基础校验
                checkParam(reqBody, page, keys);
                if (keys.length > 0) {
                    Map paramsMap = reqBody.getParamsMap();
                    for (int i = 0; i < keys.length; i++) {
                        Object object = paramsMap.get(keys[i]);
                        if (object == null) {
                            throw new GlobalException(RES_PARAM_IS_EMPTY, "沒有" + keys[i] + "参数");
                        }
                        // 校验传入的是String ,实际是对象类型
                        Object newInstance = values[i].newInstance();
                        if (object instanceof String && (newInstance instanceof String == false)) {
                            throw new GlobalException(RES_PARAM_INVALID, keys[i] + "格式错误");
                        }
                        if (object instanceof JSON) {
                            Object paramObject = null;
                            try {
                                if(object instanceof JSONObject) {
                                    if (((JSONObject) object).size() == 0) {
                                        throw new GlobalException(RES_PARAM_IS_EMPTY, keys[i] + "参数为空");
                                    }
                                    paramObject = JSONObject.toJavaObject((JSON) object, values[i]);
                                }
                                if(object instanceof JSONArray) {
                                    if (((JSONArray) object).size() == 0) {
                                        throw new GlobalException(RES_PARAM_IS_EMPTY, keys[i] + "参数为空");
                                    }
                                    paramObject = JSONObject.parseArray(((JSONArray) object).toJSONString(), values[i]);
                                }

                            } catch (Exception ex) {
                                throw new GlobalException(RES_PARAM_INVALID, keys[i] + "格式错误");
                            }
                            // 参数校验
                            ValidationUtils.validate(paramObject);
                        } else {
                            ValidationUtils.validate(object);
                        }

                    }
                }
            } else {
                Object[] args = joinPoint.getArgs();
                for (Object object : args) {
                    ValidationUtils.validate(object);
                }
            }
        }
        return joinPoint.proceed();
    }
    /**
     * 基本校验
     *
     * @param reqBody
     */
    public void checkParam(ReqBody reqBody, Boolean isPage, String... strings) {
        if (reqBody == null) {
            throw new GlobalException(RES_PARAM_IS_EMPTY, "服务器未收到信息");
        }

        if (strings != null && strings.length > 0) {
            if (reqBody.getParamsMap() == null) {
                throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数paramsMap为空");
            }
            for (String str : strings) {
                if (reqBody.getParamsMap().get(str) == null) {
                    throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数" + str + "为空");
                }
                if (StringUtils.isEmpty(reqBody.getParamsMap().get(str).toString())) {
                    throw new GlobalException(RES_PARAM_IS_EMPTY, "请求参数" + str + "为空");
                }
            }
        }
    }
}
