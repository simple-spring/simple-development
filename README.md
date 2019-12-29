### spring simple2.0 介绍
    基于ssm javaconfig 零xml配置，常用的三层结构dao，service简化开发,controller层零开发,以组件方式自由搭配，简单灵活

### 使用的技术
    主体框架为spring4.3.3开发，基于javassist,cglib,Reflections技术实现
### 目的
    极致提高研发技术,极致减少业务开发。
### 2.0版本说明
    内部优化包结构和启动代码，制定对应规范，新增组件容器启动，新增观察者模式+监听者混合模式
### 使用
##### 1.maven项目配置(加入之前先配置settings.xml[下载](http://file.diangc.cn/settings.xml))
        (1) 加入依赖
            <dependency>
                <groupId>com.chexin</groupId>
                <artifactId>simple-development-core</artifactId>
                <version>2.0</version>
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
            <!--MAVEN打包duboo可执行jar begin -->
            <build>
                <finalName>simple-development-demo</finalName>
        
                <resources>
                    <resource>
                        <directory>src/main/resources/config/${package.environment}</directory>
                        <filtering>true</filtering>
                    </resource>
                    <resource>
                        <targetPath>${project.build.directory}/classes</targetPath>
                        <directory>src/main/resources</directory>
                        <filtering>true</filtering>
                        <includes>
                            <include>**/*.properties</include>
                            <include>**/*.xml</include>
                        </includes>
                        <excludes>
                            <exclude>generatorConfig.xml</exclude>
                            <exclude>config/*/*.properties</exclude>
                        </excludes>
                    </resource>
                </resources>
        
                <pluginManagement>
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
                        <!-- 解决Maven插件在Eclipse内执行了一系列的生命周期引起冲突 -->
                        <plugin>
                            <groupId>org.eclipse.m2e</groupId>
                            <artifactId>lifecycle-mapping</artifactId>
                            <version>1.0.0</version>
                            <configuration>
                                <lifecycleMappingMetadata>
                                    <pluginExecutions>
                                        <pluginExecution>
                                            <pluginExecutionFilter>
                                                <groupId>org.apache.maven.plugins</groupId>
                                                <artifactId>maven-dependency-plugin</artifactId>
                                                <versionRange>[2.0,)</versionRange>
                                                <goals>
                                                    <goal>copy-dependencies</goal>
                                                </goals>
                                            </pluginExecutionFilter>
                                            <action>
                                                <ignore/>
                                            </action>
                                        </pluginExecution>
                                    </pluginExecutions>
                                </lifecycleMappingMetadata>
                            </configuration>
                        </plugin>
                    </plugins>
                </pluginManagement>
                <plugins>
                    <!-- 打包jar文件时，配置manifest文件，加入lib包的jar依赖 -->
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-jar-plugin</artifactId>
                        <configuration>
                            <classesDirectory>target/classes/</classesDirectory>
                            <archive>
                                <manifest>
                                    <mainClass>com.chexin.simple.development.demo.App</mainClass>
                                    <!-- 打包时 MANIFEST.MF文件不记录的时间戳版本 -->
                                    <useUniqueVersions>false</useUniqueVersions>
                                    <addClasspath>true</addClasspath>
                                    <classpathPrefix>lib/</classpathPrefix>
                                </manifest>
                                <manifestEntries>
                                    <Class-Path>.</Class-Path>
                                </manifestEntries>
                            </archive>
                        </configuration>
                    </plugin>
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-dependency-plugin</artifactId>
                        <executions>
                            <execution>
                                <id>copy-dependencies</id>
                                <phase>package</phase>
                                <goals>
                                    <goal>copy-dependencies</goal>
                                </goals>
                                <configuration>
                                    <type>jar</type>
                                    <includeTypes>jar</includeTypes>
                                    <outputDirectory>
                                        ${project.build.directory}/lib
                                    </outputDirectory>
                                </configuration>
                            </execution>
                        </executions>
                    </plugin>
                </plugins>
            </build>
#### 2.项目结构
![](http://file.diangc.cn/spring-simple2.0.jpeg)  

#### 3.使用步骤
    1.新建APP.java文件
``` Java
    
    @SpringSimpleApplication
    public class App {
        public static void main(String[] args) {
            SpringApplication.run(App.class);
        }
    }
```
    2.配置application.properties(根据组件配置)详情
`tips`

    1.SpringApplication类是启动项目的唯一入口
    2.@SpringSimpleApplication 是spring simple是基本组件，不能缺失
    3.APP.java的路径目前必须把全路径配置在maven>build>mainClass中
        
### 2.0组件列表
 组件名 | 作用 |
| --- |     --- |
| @SpringSimpleApplication| 基本组件|
| @EnableMybatis | 开启mybaits（默认数据源实现：DruidDataSource） |
| @EnableWebMvc | 开启spring mvc|
| @EnableDubbo | 开启dubbo服务 |
| @EnableRedis | 开启redsi服务(目前单节点) |
| @EnableElasticSearch| 开启elasticsearch|

##### 1.@SpringSimpleApplication组件参数说明
 参数名 | 说明 |
| --- |     --- |
| applicationName| 应用名|
| appPackagePathName | spring simple扫描bean的路径 默认是App.java启动类的包路径 |

##### 2.@EnableMybatis组件参数说明
 参数名 | 说明 |
| --- |     --- |
| mapperPath| mybatis mapper包路径 默认为appPackagePathName下面的mapper包|
| modelPath | mybatis model实体映射的包名 默认为appPackagePathName下面的model包 |
| mapperXmlPath| mybatis mapper xml路径 默认为resource下的mybatis目录下的子目录|
| expression | mybatis事务切面地址 默认为默认为appPackagePathName下面的service包 |

##### 3.@EnableWebMvc组件参数说明
| --- |     --- |
| urlPath| spring mvc 访问路径 默认为 "/"|
| packagePath | spring mvc controller 注解扫描的包路径 |
| interceptorPackagePath| spring mvc 拦截器扫描的包路径 默认为默认为appPackagePathName下面的interceptor包|

##### 4.@EnableDubbo组件参数说明
| --- |     --- |
| dubboPackage| 扫描dubbo注解@service包路径|

##### 5.@EnableRedis组件参数说明
    暂无参数


### spring simple所有的注解说明
 注解名 | 说明 |
| --- |     --- |
| @SimpleConfig| 组件父注解|
| @SpringSimpleApplication | spring simple基本组件 |
| @EnableDubbo | 开启dubbo功能 |
| @EnableElasticSearch | 开启Es功能 |
| @EnableMybatis | 开启Mybatis功能 |
| @EnableRedis | 开启Redis功能 |
| @EnableWebMvc | 开启Mvc功能 |
| @DataSource | 动态数据源切换 |
| @HasPermissions | 权限校验 |
| @Idempotent | 接口防重 |
| @IsApiService | service类名和方法名映射成地址 |
| @IsApiMethodService | 方法名映射地址修改 |
| @NoApiMethod | service方法不参与映射 |
| @NoLogin | 不需要登录的接口(自己实现登录) |
| @SimpleInterceptor | mvc 拦截器注册 |
| @Spi | 组件发现注解 |
| @ValidHandler | 参数校验 |
| @Value | 自定义application.properties配置参数映射到@value标记的变量上 |

### application.properties所有的参数说明
参数名 | 默认值样例 | 说明|
| --- |     --- |     --- |
|spring.simple.datasource.driverClassName|com.mysql.jdbc.Driver|数据库驱动|
|spring.simple.datasource.url|jdbc:mysql://172.22.5.239:3306/demo|mysql连接地址|
|spring.simple.datasource.username|root|mysql账号|
|spring.simple.datasource.password|123456|mysql密码|
|spring.simple.datasource.initialSize|20|mysql初始化连接数|
|spring.simple.datasource.minIdle|20|mysql允许的最小连接数|
|spring.simple.datasource.maxActive|150|mysql允许的最大连接数|
|spring.simple.datasource.maxWait|3000|连接超时时间|
|spring.simple.datasource.timeBetweenEvictionRunsMillis|3000|连接空闲回收每次执行时间|
|spring.simple.datasource.minEvictableIdleTimeMillis|300000|池中的连接空闲30分钟后被回收|
|spring.simple.datasource.validationQuery|SELECT 1 FROM DUAL|验证使用的SQL语句|
|spring.simple.datasource.testWhileIdle|true|指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除|
|spring.simple.datasource.testOnBorrow|false|借出连接时不要测试，否则很影响性能|
|spring.simple.datasource.testOnReturn|false|连接归还连接池的时候检测|
|spring.simple.redisHost|172.22.5.252|redis地址 目前单节点|
|spring.simple.redisPort|6379|redis端口|
|spring.simple.redisPwd|123456|redis连接密码|
|spring.simple.maxTotal|7000|redis最大连接数|
|spring.simple.maxWaitMillis|3000|redis连接超时时间|
|spring.simple.maxIdle|10|reids最小连接数|
|spring.simple.timeout|3000|reids连接超时时间|
|spring.simple.dubbo.application.name|simple-developmen|dubbp应用名|
|spring.simple.dubbo.registry.address|zookeeper://172.22.5.214:2181|dubbo-zookepper连接地址|
|spring.simple.dubbo.protocol.name|dubbo|dubbo默认协议|
|spring.simple.dubbo.protocol.port|20880|dubbo通信端口|
|spring.simple.elasticsearch.host|172.22.5.214|es服务地址 目前单节点
|spring.simple.elasticsearch.port|9300|es java client端口  服务端口为9200
|spring.simple.elasticsearch.cluster.name|elasticsearch|es集群名|


### 事件通知(异步方式)
    1.SimpleApplicationEventSubject 主题接口
        实现该接口实现两个方法
            addObserver 添加订阅者
            notifyObserver 通知
    2.SimpleApplicationListener监听者
        默认为onApplicationEvent方法 为接收通知的方法