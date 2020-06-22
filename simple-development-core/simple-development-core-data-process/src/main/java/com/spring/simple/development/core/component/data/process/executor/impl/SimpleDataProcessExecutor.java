package com.spring.simple.development.core.component.data.process.executor.impl;

import com.spring.simple.development.core.component.data.process.annotation.external.Condition;
import com.spring.simple.development.core.component.data.process.annotation.internal.SimpleActivate;
import com.spring.simple.development.core.component.data.process.executor.DataProcessExecutor;
import com.spring.simple.development.core.component.data.process.executor.mapper.SimpleMapper;
import com.spring.simple.development.core.component.data.process.extension.common.utils.StringUtils;
import com.spring.simple.development.core.init.AppInitializer;
import com.spring.simple.development.support.utils.DeclaredFieldsUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author luke
 */
@SimpleActivate
public class SimpleDataProcessExecutor implements DataProcessExecutor {

    private SimpleMapper simpleMapper = AppInitializer.rootContext.getBean(SimpleMapper.class);


    @Override
    public List invoke(Object dpoClass, Object returnClass) {
        // 场景收集
        //1，包装dpo 自定义sql
        //2,拿到数据源
        //3.数据处理()
        Class<?> dpaClass = dpoClass.getClass();
        try {
            String sql = "";
            Field[] fields = dpaClass.getDeclaredFields();
            if (fields == null) {
                sql = "select channelname,channelcode,channeltype,creditcode,legalname from t_channel";
            } else {
                sql = "select channelname,channelcode,channeltype,creditcode,legalname from t_channel where 1=1 ";
                for (Field field : fields) {
                    Condition annotation = field.getAnnotation(Condition.class);
                    if (annotation != null) {
                        Object fieldValue = DeclaredFieldsUtil.getObjByMethodName(DeclaredFieldsUtil.parGetName(field.getName()), dpaClass, null);
                        if (fieldValue == null) {
                            throw new RuntimeException(field.getName() + "参数为空");
                        }
                        if (StringUtils.isBlank(annotation.field())) {
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
