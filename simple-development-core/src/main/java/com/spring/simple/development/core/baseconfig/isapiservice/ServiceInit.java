package com.spring.simple.development.core.baseconfig.isapiservice;

import com.spring.simple.development.core.annotation.base.IsApiService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 描述:
 * 初始化服务
 *
 * @author liko
 * @create 2018-09-25 下午2:36
 */
@Component
public class ServiceInit implements ApplicationListener<ContextRefreshedEvent>{

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 根容器为Spring容器  
        System.err.println("=====ServiceInit=====" + event.getSource().getClass().getName());
        if (event.getApplicationContext().getParent() == null) {
            Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(IsApiService.class);
            for (Object bean : beans.values()) {
                if (bean != null) {
                    try {
                        Class<?> aClass = Class.forName("com.spring.simple.development.core.baseconfig.isapiservice.ServerFactory");
                        Method putService = aClass.getMethod("putService", Object.class);
                        putService.invoke(aClass.newInstance(), bean);
                    } catch (Exception e) {
                        System.out.println(e);
                        throw new RuntimeException(bean.getClass().getSimpleName() + "服务加载异常");
                    }
                }
            }
        }
    }
}