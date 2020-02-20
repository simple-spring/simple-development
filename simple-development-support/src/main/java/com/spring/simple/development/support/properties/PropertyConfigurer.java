package com.spring.simple.development.support.properties;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 项目配置文件
 */
public class PropertyConfigurer {

    private static Map<String, String> configMap = new HashMap<>();

    public static void loadApplicationProperties(String path) {
        try {
            // 读取系统配置文件
            Properties properties = new Properties();
            InputStream in = PropertyConfigurer.class.getClassLoader().getResourceAsStream(path);

            properties.load(in);

            for (Object key : properties.keySet()) {
                configMap.put(key.toString(), properties.getProperty(key.toString()));
            }
        } catch (IOException e) {
            throw new RuntimeException("load Properties  application.properties  fail");
        }
    }

    /**
     * 获取配置配置文件，不可以修改
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        String value = configMap.get(key);
        if (StringUtils.hasText(value)) {
            return value;
        }
        return null;
    }

    /**
     * 设置配置配置文件，不可以修改
     *
     * @param key
     * @return
     */
    public static void setProperty(String key, String value) {
        configMap.put(key, value);
    }

}