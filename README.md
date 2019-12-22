### simple-development1.0 介绍
    基于ssm javaconfig 零xml配置，常用的三层结构dao，service简化开发,controller层零开发

### 使用的技术
    主体框架基于spring4.3.3开发，基于javassist,cglib,Reflections技术实现

### 使用
##### 1.maven项目配置(加入之前先配置settings.xml,在最下面demo项目中)
        (1) 加入依赖
            <dependency>
                <groupId>com.chexin</groupId>
                <artifactId>simple-development-core</artifactId>
                <version>1.0</version>
                <scope>compile</scope>
            </dependency>
        (2)配置环境隔离
             <profiles>
                    <profile>
                        <id>dev</id>
                        <properties>
                            <package.environment>dev</package.environment>
                        </properties>
                        <activation>
                            <activeByDefault>true</activeByDefault>
                        </activation>
                    </profile>
                    <profile>
                        <id>net</id>
                        <properties>
                            <package.environment>net</package.environment>
                        </properties>
                    </profile>
                    <profile>
                        <id>test</id>
                        <properties>
                            <package.environment>test</package.environment>
                        </properties>
                    </profile>
                </profiles>
        (3)打包配置
            <build>
                    <finalName>simple-development-demo</finalName>
                    <plugins>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-compiler-plugin</artifactId>
                            <configuration>
                                <source>6</source>
                                <target>6</target>
                            </configuration>
                        </plugin>
                    </plugins>
                    <resources>
                        <resource>
                            <directory>src/main/resources/config/${package.environment}</directory>
                            <filtering>true</filtering>
                        </resource>
                        <resource>
                            <directory>src/main/resources</directory>
                            <excludes>
                                <exclude>generatorConfig.xml</exclude>
                                <exclude>config/*/*.properties</exclude>
                            </excludes>
                            <filtering>false</filtering>
                        </resource>
                    </resources>
                    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
                        <plugins>
                            <plugin>
                                <groupId>org.mybatis.generator</groupId>
                                <artifactId>mybatis-generator-maven-plugin</artifactId>
                                <version>1.3.7</version>
                                <configuration>
                                    <overwrite>true</overwrite>
                                    <verbose>true</verbose>
                                </configuration>
                                <dependencies>
                                    <dependency>
                                        <groupId>org.apache.maven</groupId>
                                        <artifactId>maven-project</artifactId>
                                        <version>2.2.1</version>
                                    </dependency>
                                    <!-- MySQL Connector -->
                                    <dependency>
                                        <groupId>mysql</groupId>
                                        <artifactId>mysql-connector-java</artifactId>
                                        <version>5.1.40</version>
                                    </dependency>
            
                                    <!-- MyBatis Generator -->
                                    <dependency>
                                        <groupId>com.manila.generator</groupId>
                                        <artifactId>manila.generator</artifactId>
                                        <version>1.0</version>
                                    </dependency>
                                </dependencies>
                            </plugin>
                            <plugin>
                                <artifactId>maven-clean-plugin</artifactId>
                                <version>3.0.0</version>
                            </plugin>
                            <!-- see http://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_war_packaging -->
                            <plugin>
                                <artifactId>maven-resources-plugin</artifactId>
                                <version>3.0.2</version>
                            </plugin>
                            <plugin>
                                <artifactId>maven-compiler-plugin</artifactId>
                                <version>3.7.0</version>
                            </plugin>
                            <plugin>
                                <artifactId>maven-surefire-plugin</artifactId>
                                <version>2.20.1</version>
                            </plugin>
                            <plugin>
                                <artifactId>maven-war-plugin</artifactId>
                                <version>3.2.0</version>
                            </plugin>
                            <plugin>
                                <artifactId>maven-install-plugin</artifactId>
                                <version>2.5.2</version>
                            </plugin>
                            <plugin>
                                <artifactId>maven-deploy-plugin</artifactId>
                                <version>2.8.2</version>
                            </plugin>
                        </plugins>
            
                    </pluginManagement>
            
                </build>
 
##### 2.项目结构
![项目结构图](http://file.diangc.cn/simple-development1.0.png)
      
##### 3.application.properties
    项目的resources目录下增加config下的dev环境增加application.properties，并在application.properties增加如下配置
        #项目根路径包名
        spring.base.package=com.chexin.simple.development.demo
        #mybatis xml文件路径
        mybatis.mapper.path=classpath*:mybatis/*/*.xml
        #数据源基础信息(按需设置)
        spring.datasource.driverClassName=com.mysql.jdbc.Driver
        spring.datasource.url=jdbc:mysql://127.0.0.1:3306/demo?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&autoReconnect=true&serverTimezone=CTT
        spring.datasource.username=root
        spring.datasource.password=123456
        
        #连接池配置
        spring.datasource.initialSize=20
        spring.datasource.minIdle=20
        spring.datasource.maxActive=150
        #连接等待超时时间
        spring.datasource.maxWait=3000
        #配置隔多久进行一次检测(检测可以关闭的空闲连接)
        spring.datasource.timeBetweenEvictionRunsMillis=3000
        #配置连接在池中的最小生存时间
        spring.datasource.minEvictableIdleTimeMillis=300000
        spring.datasource.validationQuery=SELECT 1 FROM DUAL
        spring.datasource.testWhileIdle=true
        spring.datasource.testOnBorrow=false
        spring.datasource.testOnReturn=false

| 包名 | 名称 | 作用 |
| --- | --- | --- |
| dao | 数据访问层（接口） | 与数据打交道，可以是数据库操作，也可以是文件读写操作，甚至是redis缓存操作，总之与数据操作有关的都放在这里，也有人叫做dal或者数据持久层都差不多意思。为什么没有daoImpl，因为我们用的是mybatis，所以可以直接在配置文件中实现接口的每个方法。 |
| model | 实体类 | 一般与数据库的表相对应，封装dao层取出来的数据为一个对象，也就是我们常说的pojo，一般只在dao层与service层之间传输。 |
| service | 业务逻辑（接口） | 写我们的业务逻辑，也有人叫bll，在设计业务接口时候应该站在“使用者”的角度。额，不要问我为什么这里没显示！IDE调皮我也拿它没办法~ |
| serviceImpl | 业务逻辑（实现） | 实现我们业务接口，一般事务控制是写在这里，没什么好说的。 |
| controller | 控制器 | springmvc就是在这里发挥作用的，一般人叫做controller控制器，相当于struts中的action。 |
| interceptor | 拦截器 | springmvc过滤请求和资源就是在这里发挥作用的，，相当于struts中的filter。 |

| 资源 | 名称 | 作用 |
| --- | --- | --- |
| config | application.properties开发，测试，线上隔离 | 利用maven环境隔离打包,配置不同环境的文件
| mybatis | mybatis的xml存放路径 | 与mapper映射
| aop-config.xml | aop声明式事务配置 | javaconfig方式使用aop配置声明式事务tx标签不好实现,则1.0先用xml代替
| log4j.properties | 日志文件 | 日志文件
| log4j2.xml | 日志文件 | 日志文件 |
| generatorConfig.xml | mybatis插件 | 自动生成代码工具

### 功能清单
` simple-development主要集中在service层的开发,大多数的封装都基于注解+aop来实现`

| 注解名 | 作用 |
| --- | --- |
| @DataSource | 动态数据源切换 |
| @HasPermissions | 权限校验|
| @Idempotent | 简单的防重校验,基于redis,若要使用这个注解则需要开启redis功能 |
| @IsApiService | 将serviceBean 注册为服务,通过反射调用方法 |
| @NoApiMethod | 将serviceBean内部的方法忽略注册服务|
| @NoLogin | 控制需要登录的接口|
| @SimpleInterceptor | 实现HandlerInterceptor的接口类加入此注解则自动注册拦截器 |
| @ValidHandler | 权限校验框架 |
| @Value | 自定义属性赋值注解 |

### 统一入参和出参
    ReqBody.java,ResBody.java
### 统一的入口
    /data/api/v1    （鉴权后的地址）
    /data/config/v1 （鉴权前的地址）
###接口定义
    ```
    public interface DemoService {
        /**
         * 查询列表
         *
         * @param reqBody
         * @return
         */
        ResBody querydemoDoList(ReqBody reqBody) throws Exception;
    }
### 启动
目前还是基于tomcat启动,将打好的war包放入webapps目录下直接启动即可

### demo地址下载
http://file.diangc.cn/simple-development-demo.zip