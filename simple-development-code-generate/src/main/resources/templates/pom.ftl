<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <artifactId>${projectName}</artifactId>
    <groupId>com.spring</groupId>
    <version>1.0</version>
    <modelVersion>4.0.0</modelVersion>

    <properties>
        <maven.compiler.target>1.8</maven.compiler.target>
        <maven.compiler.source>1.8</maven.compiler.source>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.spring</groupId>
            <artifactId>simple-development-core</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>com.spring</groupId>
            <artifactId>simple-development-core-valid</artifactId>
            <version>1.2</version>
        </dependency>
        <#list components as component>
            <#if (component == "springMvc") >
                <dependency>
                    <groupId>com.spring</groupId>
                    <artifactId>simple-development-core-mvc</artifactId>
                    <version>1.2</version>
                </dependency>
            </#if>
            <#if (component == "mybatis") >
                <dependency>
                    <groupId>com.spring</groupId>
                    <artifactId>simple-development-core-jdbc</artifactId>
                    <version>1.2</version>
                </dependency>
            </#if>
            <#if (component == "redis") >
                <dependency>
                    <groupId>com.spring</groupId>
                    <artifactId>simple-development-core-redis</artifactId>
                    <version>1.2</version>
                </dependency>
            </#if>
            <#if (component == "dubbo") >
                <dependency>
                    <groupId>com.spring</groupId>
                    <artifactId>simple-development-core-dubbo</artifactId>
                    <version>1.2</version>
                </dependency>
            </#if>
            <#if (component == "cassandra") >
                <dependency>
                    <artifactId>simple-development-core-cassandra</artifactId>
                    <groupId>com.spring</groupId>
                    <version>1.2</version>
                </dependency>
            </#if>
            <#if (component == "kafka") >
                <dependency>
                    <groupId>com.spring</groupId>
                    <artifactId>simple-development-core-kafka</artifactId>
                    <version>1.2</version>
                </dependency>
            </#if>
            <#if (component == "mongodb") >
                <dependency>
                    <artifactId>simple-development-core-mongodb</artifactId>
                    <groupId>com.spring</groupId>
                    <version>1.2</version>
                </dependency>
            </#if>
            <#if (component == "job") >
                <dependency>
                    <groupId>com.spring</groupId>
                    <artifactId>simple-development-core-job</artifactId>
                    <version>1.2</version>
                </dependency>
            </#if>
            <#if (component == "es") >
                <dependency>
                    <groupId>com.spring</groupId>
                    <artifactId>simple-development-core-es</artifactId>
                    <version>1.2</version>
                </dependency>
            </#if>
            <#if (component == "shiroCas") >
                <dependency>
                    <artifactId>simple-development-core-shiro-cas</artifactId>
                    <groupId>com.spring</groupId>
                    <version>1.2</version>
                </dependency>
            </#if>
            <#if (component == "swagger") >
                <dependency>
                    <groupId>com.spring</groupId>
                    <artifactId>simple-development-core-swagger</artifactId>
                    <version>1.2</version>
                </dependency>
            </#if>
            <#if (component == "alert") >
                <dependency>
                    <artifactId>simple-development-core-alert-sdk</artifactId>
                    <groupId>com.spring</groupId>
                    <version>1.2</version>
                </dependency>
            </#if>
            <#if (component == "generator") >
                <dependency>
                    <groupId>com.baomidou</groupId>
                    <artifactId>mybatis-plus-boot-starter</artifactId>
                    <version>3.3.1.tmp</version>
                </dependency>
                <dependency>
                    <groupId>com.baomidou</groupId>
                    <artifactId>mybatis-plus-generator</artifactId>
                    <version>3.3.1.tmp</version>
                </dependency>
                <dependency>
                    <groupId>com.ibeetl</groupId>
                    <artifactId>beetl</artifactId>
                    <version>2.9.3</version>
                </dependency>
              </#if>
        </#list>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <!--MAVEN打包duboo可执行jar begin -->
    <build>
        <finalName>${projectName}</finalName>

        <resources>
            <resource>
                <targetPath>${r'${project.build.directory}'}/classes</targetPath>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <excludes>
                    <exclude>generatorConfig.xml</exclude>
                    <exclude>log4j.properties</exclude>
                    <exclude>assembly.xml</exclude>
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
                        <!-- MyBatis Generator -->
                        <dependency>
                            <groupId>com.spring</groupId>
                            <artifactId>simple-development-generator</artifactId>
                            <version>1.2</version>
                        </dependency>
                    </dependencies>
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
                            <mainClass>${packagePath}.App</mainClass>
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
                                ${r'${project.build.directory}'}/lib
                            </outputDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <descriptors>
                        <descriptor>src/main/resources/assembly.xml</descriptor>
                    </descriptors>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
    <!--MAVEN打包duboo可执行jar end -->
    <!-- 私库 -->
    <repositories>
        <repository>
            <id>central</id>
            <url>http://47.115.89.120:8081/nexus/content/groups/public/</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
</project>