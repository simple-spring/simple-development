package com.simple.code.generate.component;

import com.simple.code.generate.config.FreeMarkerTemplate;
import com.simple.code.generate.config.SimpleConfig;
import com.simple.code.generate.dto.SimpleConfigDto;
import com.simple.code.generate.simpleenum.BaseEnum;
import com.simple.code.generate.simpleenum.ComponentEnum;
import com.simple.code.generate.simpleenum.PackageEnum;
import com.simple.code.generate.utils.ScriptUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author luke
 */
public class ComponentNameGenerate extends SimpleGenerate {


    public ComponentNameGenerate(String projectName, String projectPackage, SimpleConfigDto simpleConfigDto, List<ComponentEnum> componentEnumList) {
        super(projectName, projectPackage, simpleConfigDto, componentEnumList);
    }

    @Override
    public void generateComponent() throws Exception {

        for (ComponentEnum componentEnum : componentEnumList) {
            switch (componentEnum) {
                case springMvc:
                    generateSpringMvcComponent();
                    break;
                case mybatis:
                    generateMybatisComponent();
                    break;
                case dubbo:
                    generateDubboComponent();
                    break;
                case cassandra:
                    generateCassandraComponent();
                    break;
                case kafka:
                    generateKafkaComponent();
                    break;
                case mongodb:
                    generateMongodbComponent();
                    break;
                case job:
                    generateJobComponent();
                    break;
                default:
                    break;
            }
        }

    }

    public void generateSpringMvcComponent() throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("packagePath", projectPackage);
        FreeMarkerTemplate.generateFileByTemplate("DemoController.ftl", new File(projectPackagePath + "/" + PackageEnum.controller + "/" + "DemoController.java"), dataMap);
        FreeMarkerTemplate.generateFileByTemplate("DemoInterceptor.ftl", new File(projectPackagePath + "/" + PackageEnum.interceptor + "/" + "DemoInterceptor.java"), dataMap);
        FreeMarkerTemplate.generateFileByTemplate("DemoVo.ftl", new File(projectPackagePath + "/" + PackageEnum.vo + "/" + "DemoVo.java"), dataMap);

    }

    public void generateMybatisComponent() throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("packagePath", projectPackage);
        if (!simpleConfigDto.getMybatisIsAutoGenerate()) {
            FreeMarkerTemplate.generateFileByTemplate("DemoDoMapperExt.ftl", new File(projectPackagePath + "/" + PackageEnum.mapper + "/" + "DemoDoMapperExt.java"), dataMap);
            FreeMarkerTemplate.generateFileByTemplate("DemoDo.ftl", new File(projectPackagePath + "/" + PackageEnum.model + "/" + "DemoDo.java"), dataMap);
            FreeMarkerTemplate.generateFileByTemplate("DemoDoExample.ftl", new File(projectPackagePath + "/" + PackageEnum.model + "/" + "DemoDoExample.java"), dataMap);
            FreeMarkerTemplate.generateFileByTemplate("TestDemoBo.ftl", new File(projectPackagePath + "/" + PackageEnum.service + "/" + "TestDemoBo.java"), dataMap);
            FreeMarkerTemplate.generateFileByTemplate("TestDemoBoImpl.ftl", new File(projectPackagePath + "/" + PackageEnum.service + "/" + PackageEnum.impl + "/" + "TestDemoBoImpl.java"), dataMap);
            FreeMarkerTemplate.generateFileByTemplate("DemoDoMapperXml.ftl", new File(projectResourcesPath + "/" + BaseEnum.mybatis + "/" + projectName + "/" + "DemoDoMapper.xml"), dataMap);
            FreeMarkerTemplate.generateFileByTemplate("DemoDoMapperExtXml.ftl", new File(projectResourcesPath + "/" + BaseEnum.mybatis + "/" + projectName + "/" + "DemoDoMapperExt.xml"), dataMap);
        }
    }

    public void generateDubboComponent() throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("packagePath", projectPackage);
        FreeMarkerTemplate.generateFileByTemplate("ConsumerDubboDemoService.ftl", new File(projectPackagePath + "/" + PackageEnum.dubbo + "/" + PackageEnum.consumer + "/" + "ConsumerDubboDemoService.java"), dataMap);
        FreeMarkerTemplate.generateFileByTemplate("ConsumerDubboDemoServiceImpl.ftl", new File(projectPackagePath + "/" + PackageEnum.dubbo + "/" + PackageEnum.consumer + "/" + "ConsumerDubboDemoServiceImpl.java"), dataMap);
        FreeMarkerTemplate.generateFileByTemplate("ProviderDubboDemoService.ftl", new File(projectPackagePath + "/" + PackageEnum.dubbo + "/" + PackageEnum.provider + "/" + "ProviderDubboDemoService.java"), dataMap);
        FreeMarkerTemplate.generateFileByTemplate("ProviderDubboDemoServiceImpl.ftl", new File(projectPackagePath + "/" + PackageEnum.dubbo + "/" + PackageEnum.provider + "/" + "ProviderDubboDemoServiceImpl.java"), dataMap);

    }

    public void generateCassandraComponent() throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("packagePath", projectPackage);
        FreeMarkerTemplate.generateFileByTemplate("CassandraDemo.ftl", new File(projectPackagePath + "/" + PackageEnum.cassandra + "/" + PackageEnum.repository + "/" + "CassandraDemo.java"), dataMap);
        FreeMarkerTemplate.generateFileByTemplate("DemoCassandraDo.ftl", new File(projectPackagePath + "/" + PackageEnum.cassandra + "/" + PackageEnum.table + "/" + "DemoCassandraDo.java"), dataMap);
        FreeMarkerTemplate.generateFileByTemplate("DemoRepository.ftl", new File(projectPackagePath + "/" + PackageEnum.cassandra + "/" + PackageEnum.repository + "/" + "DemoRepository.java"), dataMap);

    }

    public void generateKafkaComponent() throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("packagePath", projectPackage);
        FreeMarkerTemplate.generateFileByTemplate("DemoTopic.ftl", new File(projectPackagePath + "/" + PackageEnum.kafka + "/" + "DemoTopic.java"), dataMap);
    }

    public void generateMongodbComponent() throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("packagePath", projectPackage);
        FreeMarkerTemplate.generateFileByTemplate("MongoDbService.ftl", new File(projectPackagePath + "/" + PackageEnum.mongodb + "/" + "MongoDbService.java"), dataMap);
        FreeMarkerTemplate.generateFileByTemplate("MongoDbServiceImpl.ftl", new File(projectPackagePath + "/" + PackageEnum.mongodb + "/" + "MongoDbServiceImpl.java"), dataMap);
    }

    public void generateJobComponent() throws Exception {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("packagePath", projectPackage);
        FreeMarkerTemplate.generateFileByTemplate("DemoTask.ftl", new File(projectPackagePath + "/" + PackageEnum.jobhandler + "/" + "DemoTask.java"), dataMap);
    }

    /**
     * 执行生成simple项目
     *
     * @return
     * @throws Exception
     */
    public String execute() throws Exception {
        // 生成项目结构
        super.generateSimpleProject();
        // 生成组件
        generateComponent();
        // 生成代码
        if (simpleConfigDto.getMybatisIsAutoGenerate()) {
            new AutoGenerateCode(projectPath).start();
        }
        System.out.println("返回地址：" + projectPath);
        return projectPath;
    }


}
