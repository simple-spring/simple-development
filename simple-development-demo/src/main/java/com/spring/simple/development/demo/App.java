package com.spring.simple.development.demo;


import com.spring.simple.development.core.annotation.config.*;
import com.spring.simple.development.core.baseconfig.tomcat.SimpleApplication;

/**
 * @author liko.wang
 * @Date 2019/12/24/024 14:20
 * @Description 程序启动
 **/
@ImportI18n(i18nPath = {"i18n/message_en.properties"})
@EnableAlert
@EnableSwagger
@EnableMybatis
@EnableWebMvc
@SpringSimpleApplication
public class App {
    public static void main(String[] args) {
        SimpleApplication.run(App.class);
    }
}