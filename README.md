### simple-development2.0 介绍
    基于ssm javaconfig 零xml配置，常用的三层结构dao，service简化开发,controller层零开发

### 使用的技术
    主体框架基于spring4.3.3开发，基于javassist,cglib,Reflections技术实现

### 使用
##### 1.maven项目配置(加入之前先配置settings.xml[下载](http://file.diangc.cn/settings.xml))
        (1) 加入依赖
            <dependency>
                <groupId>com.chexin</groupId>
                <artifactId>simple-development-core</artifactId>
                <version>2.2</version>
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
 功能清单 | 说明 |
| --- | --- |
| 集成dubbo | 分布式服务 |
| 集成es | 全文检索|
| 集成redis | 缓存数据库 |
| 自动生成代码2.0 | mybatis generator插件自定义开发 |
| 集成job| 任务调度中心|
| 自由选配 | 服务自由搭配|
| main函数启动 | 方便开发|
| swagger | 开发文档生成|
