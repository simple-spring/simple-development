package com.chexin.simple.development.core;

import com.chexin.simple.development.core.annotation.Spi;
import com.chexin.simple.development.core.annotation.config.SimpleConfig;
import com.chexin.simple.development.core.config.SimpleSpiConfig;
import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.util.CollectionUtils;

import java.util.Set;

public class SimpleTest {
    @Test
    public void test1(){
        Reflections configReflections = new Reflections("com.chexin.simple.development.core.annotation.config");
        Set<Class<?>> simpleConfigList = configReflections.getTypesAnnotatedWith(SimpleConfig.class);
        if (CollectionUtils.isEmpty(simpleConfigList) == false) {
            for(Class simpleClass:simpleConfigList){
                System.out.println(simpleClass.getSimpleName());
            }
        }
    }
}
