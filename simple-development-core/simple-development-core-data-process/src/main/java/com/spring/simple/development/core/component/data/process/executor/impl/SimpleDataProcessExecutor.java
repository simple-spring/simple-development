package com.spring.simple.development.core.component.data.process.executor.impl;

import com.spring.simple.development.core.component.data.process.annotation.external.Condition;
import com.spring.simple.development.core.component.data.process.annotation.external.SimpleDpo;
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
    public Object invoke(Class dpaClass, SimpleDpo simpleDpo) {

        try {
            String sql = "";
            Field[] fields = dpaClass.getDeclaredFields();
            if (fields == null) {
                sql = "select channelname,channelcode,channeltype,creditcode,legalname from " + simpleDpo.tableName();
            } else {
                sql = "select channelname,channelcode,channeltype,creditcode,legalname from " + simpleDpo.tableName() + " where 1=1 ";
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
            List<Object> objects = simpleMapper.executorSql(sql, simpleDpo.returnClass());
            return objects;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
