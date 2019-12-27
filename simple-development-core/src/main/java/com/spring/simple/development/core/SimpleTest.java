package com.spring.simple.development.core;

import com.spring.simple.development.core.annotation.config.SimpleComponent;
import com.spring.simple.development.core.handler.event.SimpleApplicationListener;
import org.junit.Test;
import org.reflections.Reflections;
import org.springframework.util.CollectionUtils;

import java.util.Set;

public class SimpleTest {
    @Test
    public void test1() {
        Reflections reflections = new Reflections("com.spring.simple.development.core");
        Set<Class<? extends SimpleApplicationListener>> subTypes = reflections.getSubTypesOf(SimpleApplicationListener.class);
        System.out.println(subTypes);

    }
}
