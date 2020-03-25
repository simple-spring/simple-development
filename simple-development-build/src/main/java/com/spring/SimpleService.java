package com.spring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class SimpleService {
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        URL url1 = new URL("file:D:/simple-development/simple-development-demo/target/simple-development-demo.jar");
        URLClassLoader myClassLoader1 = new URLClassLoader(new URL[] { url1 }, Thread.currentThread()
                .getContextClassLoader());
        Class<?> myClass1 = myClassLoader1.loadClass("com.spring.simple.development.demo.App");
        Object o = myClass1.newInstance();
        Method main = myClass1.getMethod("startup");
        main.invoke(o);
    }
}
