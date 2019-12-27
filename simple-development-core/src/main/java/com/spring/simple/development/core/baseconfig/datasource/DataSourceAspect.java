package com.spring.simple.development.core.baseconfig.datasource;

import com.spring.simple.development.core.annotation.base.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
 *
 * @author
 */
@Order(value = 2)
@Aspect
@Component
public class DataSourceAspect {
    private static final Logger logger = LogManager.getLogger(DataSourceAspect.class);

    @Pointcut("@annotation(com.spring.simple.development.core.annotation.base.DataSource)")
    public void annotationPointcut() {
    }

    /**
     * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
     *
     * @param point
     * @throws Exception
     */
    @Before("annotationPointcut()")
    public void before(JoinPoint point) throws Exception {
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
    }

    @After("annotationPointcut()")
    public void after() {
        // 需要验证
        DynamicDataSourceHolder.clearDataSource();
    }

    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     *
     * @param clazz
     * @param method
     */
    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                DataSource source = clazz.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.setDataSource(source.value());
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource source = m.getAnnotation(DataSource.class);
                DynamicDataSourceHolder.setDataSource(source.value());
            }
        } catch (Exception e) {
            logger.error(clazz + ":" + e.getMessage());
        }
    }


}
