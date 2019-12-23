package com.chexin.simple.development.core.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.chexin.simple.development.core.constant.PackageNameConstant;
import com.chexin.simple.development.core.constant.ValueConstant;
import com.chexin.simple.development.support.properties.PropertyConfigurer;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 15:25
 * @Description mybatis config
 **/
@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource() {
        System.out.println("Initialize the data source...");
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(ValueConstant.dbUrl);
        datasource.setUsername(ValueConstant.username);
        datasource.setPassword(ValueConstant.password);
        datasource.setDriverClassName(ValueConstant.driverClassName);

        //configuration
        datasource.setInitialSize(ValueConstant.initialSize);
        datasource.setMinIdle(ValueConstant.minIdle);
        datasource.setMaxActive(ValueConstant.maxActive);
        datasource.setMaxWait(ValueConstant.maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(ValueConstant.timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(ValueConstant.minEvictableIdleTimeMillis);
        datasource.setValidationQuery(ValueConstant.validationQuery);
        datasource.setTestWhileIdle(ValueConstant.testWhileIdle);
        datasource.setTestOnBorrow(ValueConstant.testOnBorrow);
        datasource.setTestOnReturn(ValueConstant.testOnReturn);
        return datasource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources(PropertyConfigurer.getProperty("mybatis.mapper.path")));
        sqlSessionFactoryBean.setTypeAliasesPackage(PropertyConfigurer.getProperty("spring.base.package")+ PackageNameConstant.MODEL);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper()});
        return sqlSessionFactoryBean;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("dialect", "mysql");
        pageHelper.setProperties(p);
        return pageHelper;
    }

    @Bean
    public TransactionInterceptor txAdvice() {
        // 读写
        DefaultTransactionAttribute txAttr_REQUIRED = new DefaultTransactionAttribute();
        txAttr_REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 只读
        DefaultTransactionAttribute txAttr_REQUIRED_READONLY = new DefaultTransactionAttribute();
        txAttr_REQUIRED_READONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        txAttr_REQUIRED_READONLY.setReadOnly(true);

        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        source.addTransactionalMethod("add*", txAttr_REQUIRED);
        source.addTransactionalMethod("save*", txAttr_REQUIRED);
        source.addTransactionalMethod("insert*", txAttr_REQUIRED);
        source.addTransactionalMethod("delete*", txAttr_REQUIRED);
        source.addTransactionalMethod("update*", txAttr_REQUIRED);
        source.addTransactionalMethod("exec*", txAttr_REQUIRED);
        source.addTransactionalMethod("set*", txAttr_REQUIRED);
        source.addTransactionalMethod("get*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("query*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("find*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("list*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("count*", txAttr_REQUIRED_READONLY);
        source.addTransactionalMethod("is*", txAttr_REQUIRED_READONLY);
        // 默认读写
        source.addTransactionalMethod("*", txAttr_REQUIRED);
        return new TransactionInterceptor(transactionManager(), source);
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(PropertyConfigurer.getProperty("aop.expression"));
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }

}