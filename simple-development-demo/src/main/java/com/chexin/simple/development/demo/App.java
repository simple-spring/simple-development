package com.chexin.simple.development.demo;


import com.chexin.simple.development.core.annotation.config.EnableDubbo;
import com.chexin.simple.development.core.annotation.config.EnableElasticSearch;
import com.chexin.simple.development.core.annotation.config.EnableMybatis;
import com.chexin.simple.development.core.annotation.config.EnableWebMvc;
import com.chexin.simple.development.core.annotation.config.SpringSimpleApplication;
import com.chexin.simple.development.core.tomcat.SpringApplication;

/**
 * @author liko.wang
 * @Date 2019/12/24/024 14:20
 * @Description 程序启动
 **/
//@EnableDubbo
@EnableMybatis
@EnableWebMvc
@SpringSimpleApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class);
    }
}