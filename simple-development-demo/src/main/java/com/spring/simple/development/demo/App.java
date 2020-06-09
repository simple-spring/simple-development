package com.spring.simple.development.demo;


import com.spring.simple.development.core.annotation.config.*;
import com.spring.simple.development.core.baseconfig.tomcat.SimpleApplication;
import com.spring.simple.development.demo.service.TestDemoBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * @author liko.wang
 * @Date 2019/12/24/024 14:20
 * @Description 程序启动
 **/
//@EnableAlert
//@EnableXxlJob
@EnableSwagger
@EnableMybatis
@EnableRedis
@EnableWebMvc
//@EnableDubbo
//@EnableCassandra
//@EnableShiroCas
@SpringSimpleApplication
public class App {
    @Autowired
    private TestDemoBo testDemoBo;

    @Test
    public void test() {
        SimpleApplication.runTest(App.class);
        testDemoBo.getData("1");
    }
}