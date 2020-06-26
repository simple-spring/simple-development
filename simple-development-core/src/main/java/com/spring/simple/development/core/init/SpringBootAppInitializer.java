package com.spring.simple.development.core.init;

import com.spring.simple.development.core.annotation.base.Spi;
import com.spring.simple.development.core.component.ComponentContainer;
import com.spring.simple.development.core.spiconfig.SimpleSpiConfig;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.reflections.Reflections;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 12:48
 * @Description 服务初始化
 **/
public class SpringBootAppInitializer {

    public transient static List<String> packageNames = new CopyOnWriteArrayList<>();
    /**
     * 组件寄存器
     */
    public static Set<Annotation> annotationSet = new HashSet<>();

    /**
     * 选配的组件寄存器
     */
    public static Map<String, Annotation> annotationMap = new HashMap<>();

    public static List<String> getComponents() {

        try {
            System.out.println("spring simple start");
            System.out.println("spring simple config init ...");
            // 读取项目配置文件
            PropertyConfigurer.loadApplicationProperties("application.properties");
            System.out.println("spring simple config end");

            // 获取main类的包路径
            String basePackageName = System.getProperty(SystemProperties.APPLICATION_ROOT_CONFIG_APP_PACKAGE_PATH_NAME);
            if (StringUtils.isEmpty(basePackageName)) {
                System.out.println("spring simple base package is empty");
                return null;
            }
            if (CollectionUtils.isEmpty(annotationSet)) {
                System.out.println("not enabled spring simple component");
                return null;
            }
            // 组件容器初始化
            ComponentContainer.initComponentContainer();

            for (Annotation annotation : annotationSet) {
                // 选择加载的组件
                if (ComponentContainer.Components.containsKey(annotation.annotationType().getName())) {
                    System.out.println(ComponentContainer.Components.get(annotation.annotationType().getName()).getSimpleName() + "init");
                    annotationMap.put(ComponentContainer.Components.get(annotation.annotationType().getName()).getSimpleName(), annotation);
                }
            }
            // 是否有启动的组件
            if (CollectionUtils.isEmpty(annotationMap)) {
                System.out.println("not enabled spring simple component");
                return null;
            }


            // 获取所有的组件注解实现
            System.out.println("Reflections SimpleSpi start");
            Reflections sipReflections = new Reflections(SimpleSpiConfig.class);
            Set<Class<?>> classes = sipReflections.getTypesAnnotatedWith(Spi.class);
            System.out.println("Reflections SimpleSpi end");
            if (CollectionUtils.isEmpty(classes)) {
                throw new RuntimeException("spring simple component is empty");
            }
            // 通过spi找到组件
            for (Class configClass : classes) {
                Spi spi = (Spi) configClass.getAnnotation(Spi.class);
                // 获取组件名
                String configName = spi.configName();
                if (annotationMap.containsKey(configName)) {
                    Object configObject = configClass.newInstance();
                    Method method = configClass.getDeclaredMethod(SystemProperties.CONFIG_METHOD_NAME, annotationMap.get(configName).annotationType());
                    Class resultClass = (Class) method.invoke(configObject, annotationMap.get(configName));
                    if (resultClass == null) {
                        continue;
                    }
                }
            }
            if (CollectionUtils.isEmpty(packageNames)) {
                System.out.println("not enabled spring simple");
                return null;
            }
            return packageNames;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("spring simple initialized fail", e);
        }
    }
}