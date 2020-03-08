#### 1.在pom文件build节点添加自动生成代码依赖
```xml
  <!--自动生成代码-->
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
                            <version>1.0</version>
                        </dependency>
                    </dependencies>
                </plugin>
            </plugins>
        </pluginManagement>
```
#### 2.在mysql新增表
``` sql
create table t_demo
(
    id           bigint auto_increment
        primary key,
    gmt_create   datetime    null comment '创建时间',
    creator      varchar(64) null comment '创建人',
    gmt_modified datetime    null comment '修改时间',
    modifier     varchar(64) null comment '修改人',
    is_deleted   varchar(1)  null comment '是否删除',
    name         varchar(32) null,
    value        varchar(32) null
)
    comment 'demo表' charset = utf8;
```

#### 3.配置generatorConfig.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>

    <!-- 生成dal配置 -->
    <context id="simple-demo" targetRuntime="MyBatis3">
        <property name="suppressAllComments" value="true"/>
        <property name="useActualColumnNames" value="false"/>

        <!-- 配置插件 -->
        <plugin type="com.alibaba.lava.dal.common.MultiDbPaginationPlugin"/>
        <plugin type="com.alibaba.lava.dal.common.BoPlugin">
        <!--    项目src/main/java在本地磁盘绝对路径    -->
            <property name="basePath"
                      value="D:/simple-development/simple-development-demo/src/main/java"/>
        </plugin>
        <plugin type="com.spring.simple.development.generator.plugin.LombokPlugin"/>

        <!-- 自定义注释生成器 -->
        <commentGenerator type="com.spring.simple.development.generator.MySQLCommentGenerator">
            <property name="author" value="liko"/>
            <property name="dateFormat" value="yyyy/MM/dd"/>
        </commentGenerator>

        <!-- 配置连接数据信息 -->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://127.0.0.1:3306/demo?characterEncoding=utf8"
                        userId="root" password="123456">
        </jdbcConnection>
        <javaTypeResolver>
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>

        <!-- 生成文件model的包路径 -->
        <javaModelGenerator targetPackage="com.spring.simple.development.demo.model"
                            targetProject="src/main/java">
            <property name="enableSubPackages" value="true"/>
            <property name="trimStrings" value="true"/>
        </javaModelGenerator>
        <!-- 生成文件mybatis xml文件的包路径-->
        <!-- demo是mybatis下级文件名,可为空-->
        <sqlMapGenerator targetPackage="demo"
                         targetProject="src/main/resources/mybatis/">
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>
        <!-- 生成文件mybatis mapper文件的包路径 -->
        <javaClientGenerator type="XMLMAPPER"
                             targetPackage="com.spring.simple.development.demo.mapper" targetProject="src/main/java">
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>
        <!-- 数据库表名t_demo 对应的model实体名 -->
        <table tableName="t_demo" domainObjectName="DemoDo">
            <!-- 默认false -->
            <property name="stateAction" value="false"/>
            <!-- 生成service对应的包名 -->
            <property name="boPackage" value="com.spring.simple.development.demo.service"/>
            <generatedKey column="id" sqlStatement="MySql" identity="true"/>
            <!-- 生成代码插件需忽略已经字段 -->
            <ignoreColumn   column="ID" />
            <ignoreColumn 	 column="GMT_CREATE" />
            <ignoreColumn 	 column="CREATOR" />
            <ignoreColumn 	 column="GMT_MODIFIED" />
            <ignoreColumn   column="MODIFIER" />
            <ignoreColumn   column="IS_DELETED" />
        </table>
    </context>
</generatorConfiguration>
```
#### 4.执行maven命令生成代码(需在项目根路径执行)
```xml
mvn mybatis-generator:generate
```
