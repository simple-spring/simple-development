package com.simple.code.generate.component;

import com.google.common.base.CaseFormat;
import com.simple.code.generate.config.FreeMarkerTemplate;
import com.simple.code.generate.config.SimpleConfig;
import com.simple.code.generate.dto.SimpleConfigDto;
import com.simple.code.generate.simpleenum.BaseEnum;
import com.simple.code.generate.simpleenum.ComponentEnum;
import com.simple.code.generate.simpleenum.PackageEnum;
import com.simple.code.generate.utils.ConnectionUtil;
import com.simple.code.generate.utils.StringUtil;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author luke
 */
public abstract class SimpleGenerate {

    /**
     * 项目名
     */
    public String projectName;
    /**
     * 项目包名
     */
    public String projectPackage;
    /**
     * 配置参数
     */
    public SimpleConfigDto simpleConfigDto;

    /**
     * 项目包路径
     */
    public String projectPackagePath;
    /**
     * 项目名路径
     */
    public String projectPath;
    /**
     * 项目java路径
     */
    public String projectJavaPath;

    /**
     * 项目test路径
     */
    public String projectTestPath;
    /**
     * 项目test包路径
     */
    public String projectTestPackagePath;
    /**
     * 项目资源路径
     */
    public String projectResourcesPath;

    public List<ComponentEnum> componentEnumList;

    public SimpleGenerate(String projectName, String projectPackage, SimpleConfigDto simpleConfigDto, List<ComponentEnum> componentEnumList) {
        this.projectName = projectName;
        this.projectPackage = projectPackage;
        this.simpleConfigDto = simpleConfigDto;
        this.componentEnumList = componentEnumList;
    }

    /**
     * generate start
     *
     * @throws Exception
     */
    public void generateSimpleProject() throws Exception {
        // 初始化结构
        projectInit();
        // 生成pom.xml
        generatePom();
        // 生成log4j2.xml
        generateLog4j2();
        // assembly.xml
        generateAssembly();
        // generatorConfig.xml
        generateGeneratorConfig();
        // README.md
        generateREADME();
        // .gitignore
        generateGitIgnore();
        // App.java
        generateApp();
        // TestApp.java
        generateTestApp();
    }

    /**
     * 组件代码生成
     */
    public abstract void generateComponent() throws Exception;


    /**
     * 生成项目结构
     */
    private void projectInit() {
        // 生成基本路径
        mkdirFile(SimpleConfig.projectGenerateBasePath);

        // 生成项目名
        projectPath = SimpleConfig.projectGenerateBasePath + projectName;
        mkdirFile(SimpleConfig.projectGenerateBasePath + projectName);

        // 生成src
        mkdirFile(SimpleConfig.projectGenerateBasePath + projectName + "/" + BaseEnum.src);

        // 生成main
        mkdirFile(SimpleConfig.projectGenerateBasePath + projectName + "/" + BaseEnum.src + "/" + BaseEnum.main);

        // 生成test
        projectTestPath = SimpleConfig.projectGenerateBasePath + projectName + "/" + BaseEnum.src + "/" + BaseEnum.test;
        mkdirFile(projectTestPath);

        // 生成java
        projectJavaPath = SimpleConfig.projectGenerateBasePath + projectName + "/" + BaseEnum.src + "/" + BaseEnum.main + "/" + BaseEnum.java;
        mkdirFile(projectJavaPath);

        // 生成resources
        projectResourcesPath = SimpleConfig.projectGenerateBasePath + projectName + "/" + BaseEnum.src + "/" + BaseEnum.main + "/" + BaseEnum.resources;
        mkdirFile(projectResourcesPath);

        // 生成存放mybatis的xml文件夹
        mkdirFile(SimpleConfig.projectGenerateBasePath + projectName + "/" + BaseEnum.src + "/" + BaseEnum.main + "/" + BaseEnum.resources + "/" + BaseEnum.mybatis);
        // 生成存放mybatis的xml文件夹下的项目文件夹
        mkdirFile(SimpleConfig.projectGenerateBasePath + projectName + "/" + BaseEnum.src + "/" + BaseEnum.main + "/" + BaseEnum.resources + "/" + BaseEnum.mybatis + "/" + projectName);

        // 生成包名
        String[] packages = StringUtil.split(projectPackage, '.');
        String currentPath = projectJavaPath;
        for (int i = 0; i < packages.length; i++) {
            mkdirFile(currentPath + "/" + packages[i]);
            currentPath = currentPath + "/" + packages[i];
            if (i == packages.length - 1) {
                projectPackagePath = currentPath;
            }
        }
        // 生成包路径
        mkdirFile(projectPackagePath + "/" + PackageEnum.controller);
        mkdirFile(projectPackagePath + "/" + PackageEnum.model);
        mkdirFile(projectPackagePath + "/" + PackageEnum.service);
        mkdirFile(projectPackagePath + "/" + PackageEnum.service + "/" + PackageEnum.impl);
        mkdirFile(projectPackagePath + "/" + PackageEnum.vo);
        mkdirFile(projectPackagePath + "/" + PackageEnum.mapper);
        mkdirFile(projectPackagePath + "/" + PackageEnum.interceptor);
        mkdirFile(projectPackagePath + "/" + PackageEnum.cassandra);
        mkdirFile(projectPackagePath + "/" + PackageEnum.cassandra + "/" + PackageEnum.repository);
        mkdirFile(projectPackagePath + "/" + PackageEnum.cassandra + "/" + PackageEnum.table);
        mkdirFile(projectPackagePath + "/" + PackageEnum.dubbo);
        mkdirFile(projectPackagePath + "/" + PackageEnum.dubbo + "/" + PackageEnum.provider);
        mkdirFile(projectPackagePath + "/" + PackageEnum.dubbo + "/" + PackageEnum.consumer);
        mkdirFile(projectPackagePath + "/" + PackageEnum.kafka);
        mkdirFile(projectPackagePath + "/" + PackageEnum.mongodb);
        mkdirFile(projectPackagePath + "/" + PackageEnum.jobhandler);

        // 生成test包名
        String testCurrentPath = projectTestPath;
        for (int i = 0; i < packages.length; i++) {
            mkdirFile(testCurrentPath + "/" + packages[i]);
            testCurrentPath = testCurrentPath + "/" + packages[i];
            if (i == packages.length - 1) {
                projectTestPackagePath = testCurrentPath;
            }
        }

    }

    /**
     * 生成pom.xml
     */
    private void generatePom() throws Exception {
        String suffix = "pom.xml";
        String path = projectPath + "/" + suffix;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("projectName", projectName);
        dataMap.put("packagePath", projectPackage);
        dataMap.put("components", componentEnumList);
        FreeMarkerTemplate.generateFileByTemplate("pom.ftl", file, dataMap);
    }

    /**
     * 生成log4j2.xml
     */
    private void generateLog4j2() throws Exception {
        // log4j2.xml
        String suffix = "log4j2.xml";
        String path = projectResourcesPath + "/" + suffix;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("projectName", projectName);
        FreeMarkerTemplate.generateFileByTemplate("log4j2.ftl", file, dataMap);
    }

    /**
     * 生成assembly.xml
     */
    private void generateAssembly() throws Exception {
        // log4j2.xml
        String suffix = "assembly.xml";
        String path = projectResourcesPath + "/" + suffix;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        Map<String, Object> dataMap = new HashMap<>();
        FreeMarkerTemplate.generateFileByTemplate("assembly.ftl", file, dataMap);
    }

    /**
     * 生成generatorConfig.xml
     */
    private void generateGeneratorConfig() throws Exception {
        // log4j2.xml
        String suffix = "generatorConfig.xml";
        String path = projectResourcesPath + "/" + suffix;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("packagePath", projectPackage);
        dataMap.put("projectName", projectName);
        dataMap.put("basePath", projectJavaPath);
        if (simpleConfigDto.getMybatisIsAutoGenerate() == true) {
            dataMap.put("mybatisIsAutoGenerate", simpleConfigDto.getMybatisIsAutoGenerate());
            dataMap.put("mysqlIp", simpleConfigDto.getMysqlIp());
            dataMap.put("mysqlPort", simpleConfigDto.getMysqlPort());
            dataMap.put("dataBaseName", simpleConfigDto.getDataBaseName());
            dataMap.put("mysqlUser", simpleConfigDto.getMysqlUser());
            dataMap.put("mysqlPassword", simpleConfigDto.getMysqlPassword());

            List<String> tableList = ConnectionUtil.getMetaData(simpleConfigDto.getMysqlIp(), simpleConfigDto.getMysqlPort(), simpleConfigDto.getMysqlUser(), simpleConfigDto.getMysqlPassword(), simpleConfigDto.getDataBaseName());
            Map<String, String> tableMap = new HashMap<>();
            for (String table : tableList) {
                String tableName;
                if (table.substring(0, 2).equals("t_") || table.substring(0, 2).equals("T_")) {
                    tableName = table.substring(2);
                } else {
                    tableName = table;
                }
                tableMap.put(table, CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName) + "Do");
            }
            dataMap.put("tables", tableMap);

        }
        FreeMarkerTemplate.generateFileByTemplate("generatorConfig.ftl", file, dataMap);
    }

    /**
     * 生成README.md
     */
    private void generateREADME() throws Exception {
        // log4j2.xml
        String suffix = "README.md";
        String path = projectResourcesPath + "/" + suffix;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        Map<String, Object> dataMap = new HashMap<>();
        FreeMarkerTemplate.generateFileByTemplate("README.ftl", file, dataMap);
    }

    /**
     * 生成.gitignore
     */
    private void generateGitIgnore() throws Exception {
        // log4j2.xml
        String suffix = ".gitignore";
        String path = projectPath + "/" + suffix;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        Map<String, Object> dataMap = new HashMap<>();
        FreeMarkerTemplate.generateFileByTemplate(".gitignore.ftl", file, dataMap);
    }

    /**
     * 生成App.java
     */
    private void generateApp() throws Exception {
        // log4j2.xml
        String suffix = "App.java";
        String path = projectPackagePath + "/" + suffix;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("packagePath", projectPackage);
        dataMap.put("branchName", simpleConfigDto.getBranchName());
        dataMap.put("projectCode", simpleConfigDto.getProjectCode());
        dataMap.put("fastGoServer", simpleConfigDto.getFastGoServer());
        dataMap.put("components", componentEnumList);
        FreeMarkerTemplate.generateFileByTemplate("App.ftl", file, dataMap);
    }

    /**
     * 生成TestApp.java
     */
    private void generateTestApp() throws Exception {
        // log4j2.xml
        String suffix = "TestApp.java";
        String path = projectTestPackagePath + "/" + suffix;
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("packagePath", projectPackage);
        dataMap.put("branchName", simpleConfigDto.getBranchName());
        dataMap.put("projectCode", simpleConfigDto.getProjectCode());
        dataMap.put("fastGoServer", simpleConfigDto.getFastGoServer());
        dataMap.put("components", componentEnumList);
        dataMap.put("mybatisIsAutoGenerate", simpleConfigDto.getMybatisIsAutoGenerate());
        FreeMarkerTemplate.generateFileByTemplate("TestApp.ftl", file, dataMap);
    }

    /**
     * 生成文件夹
     *
     * @param filePathName
     */
    private void mkdirFile(String filePathName) {
        File file = new File(filePathName);
        if (file.exists()) {
            deleteAllByPath(file);
        }
        file.mkdirs();
    }

    /**
     * 删除某个目录下所有文件及文件夹
     *
     * @param rootFilePath 根目录
     * @return boolean
     */
    private boolean deleteAllByPath(File rootFilePath) {
        File[] needToDeleteFiles = rootFilePath.listFiles();
        if (needToDeleteFiles == null) {
            return true;
        }
        for (int i = 0; i < needToDeleteFiles.length; i++) {
            if (needToDeleteFiles[i].isDirectory()) {
                deleteAllByPath(needToDeleteFiles[i]);
            }
            try {
                Files.delete(needToDeleteFiles[i].toPath());
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    public String upperFirstLatter(String letter) {
        char[] chars = letter.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char) (chars[0] - 32);
        }
        return new String(chars);
    }
}
