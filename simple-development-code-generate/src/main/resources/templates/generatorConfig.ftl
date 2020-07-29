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
            <property name="basePath"
                      value="${basePath}"/>
        </plugin>
        <plugin type="com.spring.simple.development.generator.plugin.LombokPlugin"/>

        <!-- 自定义注释生成器 -->
        <commentGenerator type="com.spring.simple.development.generator.MySQLCommentGenerator">
            <property name="author" value="liko"/>
            <property name="dateFormat" value="yyyy/MM/dd"/>
        </commentGenerator>
        <#if mybatisIsAutoGenerate??>
        <!-- 配置连接数据信息 -->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://${mysqlIp}:${mysqlPort}/${dataBaseName}?characterEncoding=utf8"
                        userId="${mysqlUser}" password="${mysqlPassword}">
        </jdbcConnection>
        <#else>
        <!-- 配置连接数据信息 -->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver"
                        connectionURL="jdbc:mysql://127.0.0.1:3306/insurance?characterEncoding=utf8"
                        userId="root" password="123456">
        </jdbcConnection>
        </#if>
        <javaTypeResolver>
            <property name="forceBigDecimals" value="false"/>
        </javaTypeResolver>

        <!-- 生成文件位置 -->
        <javaModelGenerator targetPackage="${packagePath}.model"
                            targetProject="src/main/java">
            <property name="enableSubPackages" value="true"/>
            <property name="trimStrings" value="true"/>
        </javaModelGenerator>
        <sqlMapGenerator targetPackage="${projectName}"
                         targetProject="src/main/resources/mybatis/">
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>
        <javaClientGenerator type="XMLMAPPER"
                             targetPackage="${packagePath}.mapper" targetProject="src/main/java">
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>

        <#if mybatisIsAutoGenerate??>

            <#list tables?keys as tableName>
                <table tableName="${tableName}" domainObjectName="${tables["${tableName}"]}">
                    <property name="stateAction" value="false"/>
                    <property name="boPackage" value="${packagePath}.service"/>
                    <generatedKey column="id" sqlStatement="MySql" identity="true"/>
                    <ignoreColumn   column="ID" />
                    <ignoreColumn 	column="gmt_create" />
                    <ignoreColumn 	column="creator" />
                    <ignoreColumn 	column="gmt_modified" />
                    <ignoreColumn   column="modifier" />
                    <ignoreColumn   column="is_deleted" />
                </table>
            </#list>
        </#if>
    </context>
</generatorConfiguration>