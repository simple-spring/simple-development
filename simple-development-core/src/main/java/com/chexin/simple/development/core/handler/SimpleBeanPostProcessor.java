package com.chexin.simple.development.core.handler;


import com.chexin.simple.development.core.annotation.Value;
import com.chexin.simple.development.support.properties.PropertyConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @author liko.wang
 * @Date 2019/12/20/020 13:07
 * @Description //TODO
 **/
@Component
public class SimpleBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
//        // 属性赋值
//        Field[] declaredFields = o.getClass().getDeclaredFields();
//        Value annotation = declaredFields[0].getAnnotation(Value.class);
//        if(annotation == null){
//            return o;
//        }
//        String key = annotation.value();
//        try {
//            declaredFields[0].set(o, PropertyConfigurer.getProperty(key));
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
       return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}