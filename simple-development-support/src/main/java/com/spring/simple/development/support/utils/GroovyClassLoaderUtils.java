package com.spring.simple.development.support.utils;

import groovy.lang.GroovyClassLoader;

/**
 * @author liko.wang
 * @Date 2020/1/15/015 15:23
 * @Description //TODO
 **/
public class GroovyClassLoaderUtils {
    private static GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    public static Class loadNewInstance(String codeSource) {
        if (codeSource != null && codeSource.trim().length() > 0) {
            return groovyClassLoader.parseClass(codeSource);
        }
        throw new IllegalArgumentException(">>>>>>>>>>> groovy, loadNewInstance error, instance is null");
    }
}