### simple-development2.0 介绍
    基于ssm javaconfig 零xml配置，常用的三层结构dao，service简化开发,controller层零开发

### 使用的技术
    主体框架基于spring4.3.3开发，基于javassist,cglib,Reflections技术实现

### 使用
##### 1.maven项目配置(加入之前先配置settings.xml[下载](http://file.diangc.cn/settings.xml))
        (1) 加入依赖
            <dependency>
                <groupId>com.spring</groupId>
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
### 开发中
 功能清单 | 作用 |
| --- | --- |
| 集成dubbo | 分布式服务 |
| 集成es | 全文检索|
| 集成redis | 缓存数据库 |
| 自动生成代码2.0 | mybatis generator插件自定义开发 |
| 集成job| 任务调度中心|
| 自由选配 | 服务自由搭配|
