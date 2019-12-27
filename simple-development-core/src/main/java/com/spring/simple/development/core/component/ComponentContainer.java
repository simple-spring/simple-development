package com.spring.simple.development.core.component;

import com.spring.simple.development.core.annotation.config.SimpleComponent;
import org.reflections.Reflections;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 组件小助手
 *
 * @author liko wang
 */
public class ComponentContainer {
    public static Map<String, Class> Components = new HashMap<>();

    public static void initComponentContainer() {
        // 获取所有的组件注解
        Reflections configReflections = new Reflections(SimpleComponent.class);
        Set<Class<?>> simpleConfigList = configReflections.getTypesAnnotatedWith(SimpleComponent.class);
        if (CollectionUtils.isEmpty(simpleConfigList) == false) {
            for (Class simpleClass : simpleConfigList) {
                Components.put(simpleClass.getName(), simpleClass);
            }
        }
    }
}
