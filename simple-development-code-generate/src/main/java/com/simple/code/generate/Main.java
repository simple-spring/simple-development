package com.simple.code.generate;

import com.simple.code.generate.component.ComponentNameGenerate;
import com.simple.code.generate.dto.SimpleConfigDto;
import com.simple.code.generate.simpleenum.ComponentEnum;

import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws Exception {

        SimpleConfigDto simpleConfigDto = new SimpleConfigDto();
        simpleConfigDto.setBranchName("dev");
        simpleConfigDto.setProjectCode("1234");
        simpleConfigDto.setFastGoServer("http://tech20.com");
        // mybatis自动生成代码
        simpleConfigDto.setMybatisIsAutoGenerate(true);
        simpleConfigDto.setMysqlIp("127.0.0.1");
        simpleConfigDto.setMysqlPort("3306");
        simpleConfigDto.setDataBaseName("insurance");
        simpleConfigDto.setMysqlUser("root");
        simpleConfigDto.setMysqlPassword("123456");

        List<ComponentEnum> componentEnums = new ArrayList<>();
        componentEnums.add(ComponentEnum.springMvc);
        componentEnums.add(ComponentEnum.mybatis);
//        componentEnums.add(ComponentEnum.dubbo);
//        componentEnums.add(ComponentEnum.cassandra);
//        componentEnums.add(ComponentEnum.kafka);
//        componentEnums.add(ComponentEnum.mongodb);
//        componentEnums.add(ComponentEnum.job);
//        componentEnums.add(ComponentEnum.es);
//        componentEnums.add(ComponentEnum.shiroCas);
        componentEnums.add(ComponentEnum.swagger);
//        componentEnums.add(ComponentEnum.alert);
        String projectPath = new ComponentNameGenerate("simpleDemo", "com.simple.test", simpleConfigDto, componentEnums).execute();
        System.out.println(projectPath);
    }
}
