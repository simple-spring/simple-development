//package com.spring.simple.development.core.baseconfig.vaule;
//
//
//import com.spring.simple.development.core.annotation.base.Value;
//import com.spring.simple.development.support.properties.PropertyConfigurer;
//import org.reflections.Reflections;
//import org.reflections.scanners.FieldAnnotationsScanner;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.lang.reflect.Field;
//import java.util.Set;
//
//@Component
//public class SimpleBeanPostProcessor implements BeanPostProcessor {
//    @Override
//    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
//        Service service = o.getClass().getAnnotation(Service.class);
//        if (service == null) {
//            return o;
//        }
//        System.out.println("Reflections value start");
//        Set<Field> fields = new Reflections(o.getClass().getName(), new FieldAnnotationsScanner()).getFieldsAnnotatedWith(Value.class);
//        System.out.println("Reflections value end");
//
//        if (CollectionUtils.isEmpty(fields)) {
//            return o;
//        }
//        for (Field field : fields) {
//            Value annotation = field.getAnnotation(Value.class);
//            String key = annotation.value();
//            String value = PropertyConfigurer.getProperty(key);
//            try {
//                field.setAccessible(true);
//                field.set(o, value);
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException("@value 属性赋值失败");
//            }
//        }
//        return o;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
//        return o;
//    }
//
//
//}
