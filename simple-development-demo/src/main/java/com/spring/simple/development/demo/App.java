package com.spring.simple.development.demo;


import com.spring.simple.development.core.annotation.config.*;
import com.spring.simple.development.core.baseconfig.tomcat.SimpleBootApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liko.wang
 * @Date 2019/12/24/024 14:20
 * @Description 程序启动
 **/
//@EnableDubbo
@EnableSwagger
@EnableMybatis
@EnableWebMvc
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SimpleBootApplication.run(App.class, args);
    }
}