package com.spring.simple.development.core;

import com.spring.simple.development.core.annotation.config.SimpleConfig;
import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.util.CollectionUtils;

import java.util.Set;

public class SimpleTest {
    @Test
    public void test1(){
        Reflections configReflections = new Reflections("com.spring.simple.development.core.annotation.config");
        Set<Class<?>> simpleConfigList = configReflections.getTypesAnnotatedWith(SimpleConfig.class);
        if (CollectionUtils.isEmpty(simpleConfigList) == false) {
            for(Class simpleClass:simpleConfigList){
                System.out.println(simpleClass.getSimpleName());
            }
        }
    }
}
