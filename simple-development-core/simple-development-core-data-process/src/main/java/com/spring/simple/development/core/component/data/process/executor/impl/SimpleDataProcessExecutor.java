package com.spring.simple.development.core.component.data.process.executor.impl;

import com.spring.simple.development.core.component.data.process.annotation.external.Condition;
import com.spring.simple.development.core.component.data.process.annotation.internal.SimpleActivate;
import com.spring.simple.development.core.component.data.process.executor.DataProcessExecutor;
import com.spring.simple.development.core.component.data.process.executor.mapper.SimpleMapper;
import com.spring.simple.development.core.init.AppInitializer;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.exception.ResponseCode;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author luke
 */
@SimpleActivate
public class SimpleDataProcessExecutor implements DataProcessExecutor {

    private SimpleMapper simpleMapper = AppInitializer.rootContext.getBean(SimpleMapper.class);


    @Override
    public List invoke(Object dpoObject, Object returnClass) {
        // 场景收集
        //1，包装dpo
        //2,拿到数据源自定义sql
        //3.数据处理()
        Class<?> dpoClass = dpoObject.getClass();
        try {
            String sql = "";
            Field[] fields = dpoClass.getDeclaredFields();
            if (fields == null) {
                sql = "select channelname,channelcode,channeltype,creditcode,legalname from t_channel";
            } else {
                sql = "select channelname,channelcode,channeltype,creditcode,legalname from t_channel where 1=1 ";
                for (Field field : fields) {
                    Condition annotation = field.getAnnotation(Condition.class);
                    if (annotation != null) {
                        field.setAccessible(true);
                        Object fieldValue = field.get(dpoObject);
                        if (StringUtils.isEmpty(fieldValue)) {
                            throw new GlobalException(ResponseCode.RES_PARAM_IS_EMPTY, field.getName() + "参数为空");
                        }
                        if (StringUtils.isEmpty(annotation.field())) {
                            sql += "and" + field.getName() + "=" + fieldValue;
                        } else {
                            sql += "and" + annotation.field() + "=" + fieldValue;
                        }
                    }
                }
            }
            List objects = simpleMapper.executorSql(sql, returnClass);
            return objects;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
