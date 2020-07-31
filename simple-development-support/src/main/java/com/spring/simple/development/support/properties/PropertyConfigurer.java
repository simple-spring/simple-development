package com.spring.simple.development.support.properties;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 项目配置文件
 *
 * @author luke
 */
public class PropertyConfigurer {

    private static Map<String, String> configMap = new HashMap<>();

    public static void loadApplicationProperties(String path) {
        try {
            // 读取系统配置文件
            Properties properties = new Properties();
            properties.load(new InputStreamReader(PropertyConfigurer.class.getClassLoader().getResourceAsStream(path), "UTF-8"));
            for (Object key : properties.keySet()) {
                configMap.put(key.toString(), properties.getProperty(key.toString()));
            }
        } catch (IOException e) {
            throw new RuntimeException("load Properties  application.properties  fail");
        }
    }

    public static void loadApplicationProperties(Map<String,String> map) {
        // 读取系统配置文件
        for(Map.Entry<String, String> entry : map.entrySet()){
            configMap.put(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 获取配置配置文件，不可以修改
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        if(configMap.get(key) == null){
            return null;
        }
        String value = String.valueOf(configMap.get(key));
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