#### 1.使用Ieda新建maven 
    选择模板:maven-archetype-quickstart

#### 2.根据新建的项目构建如下结构
```
src
└───main
│   │
│   └───java
│   │    │   com.spring.simple.development.demo
│   │    │   │  context(可选)
│   │    │   │  controller
│   │    │   │  interceptor
│   │    │   │  mapper
│   │    │   │  model
│   │    │   │  service
│   │    │   │  support(可选)
│   │    │   App
│   └────resources
│        │   mybatis
│        │   config
│        │   │   │
│        │   │   └───dev
│        │   │   │   └───application.properties
│        │   │   └───test
│        │   │   │   └───application.properties
│        │   │   └───net
│        │   │   │   └───application.properties
│        │   generatorConfig.xml
│        │   log4j2.xml
pom.xml
```
#### 3.说明
 参数 | 说明 |
| --- | --- |
| com.spring.simple.development.demo | 自己项目的包名 |
| context | 程序入口|
| controller | 控制层 |
| interceptor.0 | mvc拦截器 |
| mapper | mybatis mapper包路径 |
| model | 数据库数据实体 |
| service | 业务处理层 |
| support | 支持层 |
| App | 启动类 |
| resources | 资源文件夹 |
| config | 环境隔离文件夹 |
| generatorConfig.xml | 自动生成代码 |
| log4j2.xml | 日志文件 |
| dev | 开发环境 |
| test | 测试环境 |
| net | 生产环境 |
| application.properties | 系统配置文件 |