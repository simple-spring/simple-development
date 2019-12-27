package com.spring.simple.development.core.baseconfig.isapiservice;

import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.support.utils.AopTargetUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
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
public class ServiceInit implements ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {

    public Boolean isCheckPermission = true;

    public Boolean getCheckPermission() {
        return isCheckPermission;
    }

    public void setCheckPermission(Boolean checkPermission) {
        isCheckPermission = checkPermission;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 根容器为Spring容器  
        System.err.println("=====ServiceInit=====" + event.getSource().getClass().getName());
        if (event.getApplicationContext().getParent() == null) {
            Map<String, Object> beans = event.getApplicationContext().getBeansWithAnnotation(IsApiService.class);
            for (Object bean : beans.values()) {
                if (bean != null) {
                    // 加载服务
                    if (ServerFactory.isExist(bean.getClass().getSimpleName())) {
                        throw new RuntimeException(bean.getClass().getSimpleName() + "服务已存在,服务名必须唯一");
                    }
                    try {
                        ServerFactory.putService(bean);
                    } catch (Exception e) {
                        System.out.println(e);
                        throw new RuntimeException(bean.getClass().getSimpleName() + "服务加载异常");
                    }
                    if (isCheckPermission) {
                        // 加载方法权限
                        try {
                            Method[] methods = AopTargetUtils.getTarget(bean).getClass().getMethods();
                            for (Method method : methods) {
                                ServerFactory.putPermission(method);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(bean.getClass().getSimpleName() + "权限加载异常");
                        }
                    }
                }
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ServerFactory.applicationContext = applicationContext;
    }
}