package ${packagePath};

import com.spring.simple.development.core.annotation.config.*;
import com.spring.simple.development.core.annotation.config.SpringSimpleApplication;
import com.spring.simple.development.core.baseconfig.tomcat.SimpleApplication;
import ${packagePath}.service.TestDemoService;
import org.junit.Before;
import org.junit.Test;

/**
 * @author liko.wang
 * @Date 2019/12/24/024 14:20
 * @Description 程序启动
 **/
<#list components as component>
    <#if (component == "springMvc") >
@EnableWebMvc
    </#if>
    <#if (component == "mybatis") >
@EnableMybatis
    </#if>
    <#if (component == "redis") >
@EnableRedis
    </#if>
    <#if (component == "dubbo") >
@EnableDubbo
    </#if>
    <#if (component == "cassandra") >
@EnableCassandra
    </#if>
    <#if (component == "kafka") >
@EnableKafka
    </#if>
    <#if (component == "mongodb") >
@EnableMongoDB
    </#if>
    <#if (component == "job") >
@EnableXxlJob
    </#if>
    <#if (component == "es") >
@EnableElasticSearch
    </#if>
    <#if (component == "shiroCas") >
@EnableShiroCas
    </#if>
    <#if (component == "swagger") >
@EnableSwagger
    </#if>
    <#if (component == "alert") >
@EnableAlert
    </#if>
</#list>
@EnableFastGoConfig(branch = "${branchName}", projectCode = "${projectCode}", fastGoServer = "${fastGoServer}")
@SpringSimpleApplication
public class TestApp {

    @Before
    public void simpleTestBefore() {
        SimpleApplication.runTest(TestApp.class);
    }

    @Test
    public void test() {
        <#if mybatisIsAutoGenerate??>
            TestDemoService testDemoService = SimpleContentApplication.getBeanByType(TestDemoService.class);
            testDemoService.getDemoDo(1L);
        </#if>
    }
}