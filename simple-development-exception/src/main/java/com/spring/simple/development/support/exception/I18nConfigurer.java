package com.spring.simple.development.support.exception;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * I18项目配置文件
 *
 * @author luke
 */
public class I18nConfigurer {

    private static Map<String, String> I18nConfigMap = new HashMap<>();


    public static void loadI18nProperties(String path) {
        try {
            // 读取国际化文件
            Properties properties = new Properties();
            properties.load(new InputStreamReader(I18nConfigurer.class.getClassLoader().getResourceAsStream(path), "UTF-8"));
            for (Object key : properties.keySet()) {
                I18nConfigMap.put(key.toString(), properties.getProperty(key.toString()));
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
    public static String getI18nProperty(String key) {
        String value = I18nConfigMap.get(key);
        if (StringUtils.hasText(value)) {
            return value;
        }
        return null;
    }
}