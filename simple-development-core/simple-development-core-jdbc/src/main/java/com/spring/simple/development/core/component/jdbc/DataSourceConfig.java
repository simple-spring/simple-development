package com.spring.simple.development.core.component.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.spring.simple.development.core.baseconfig.datasource.DynamicDataSource;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.constant.ValueConstant;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 15:25
 * @Description mybatis config
 **/
@Configuration
public class DataSourceConfig {

    @Bean(value = "masterDataSource")
    public DataSource masterDataSource() {
        System.out.println("Initialize the masterDataSource source...");
        DruidDataSource masterDataSource = new DruidDataSource();

        masterDataSource.setUrl(ValueConstant.dbUrl);
        masterDataSource.setUsername(ValueConstant.username);
        masterDataSource.setPassword(ValueConstant.password);
        masterDataSource.setDriverClassName(ValueConstant.driverClassName);

        //configuration
        masterDataSource.setInitialSize(ValueConstant.initialSize);
        masterDataSource.setMinIdle(ValueConstant.minIdle);
        masterDataSource.setMaxActive(ValueConstant.maxActive);
        masterDataSource.setMaxWait(ValueConstant.maxWait);
        masterDataSource.setTimeBetweenEvictionRunsMillis(ValueConstant.timeBetweenEvictionRunsMillis);
        masterDataSource.setMinEvictableIdleTimeMillis(ValueConstant.minEvictableIdleTimeMillis);
        masterDataSource.setValidationQuery(ValueConstant.validationQuery);
        masterDataSource.setTestWhileIdle(ValueConstant.testWhileIdle);
        masterDataSource.setTestOnBorrow(ValueConstant.testOnBorrow);
        masterDataSource.setTestOnReturn(ValueConstant.testOnReturn);
        return masterDataSource;
    }

    @Bean(value = "slaveDataSource")
    public DataSource slaveDataSource() {
        if (ValueConstant.is_open_slave == false) {
            return masterDataSource();
        }
        System.out.println("Initialize the slaveDataSource source...");
        DruidDataSource slaveDataSource = new DruidDataSource();

        slaveDataSource.setUrl(ValueConstant.slave_dbUrl);
        slaveDataSource.setUsername(ValueConstant.slave_username);
        slaveDataSource.setPassword(ValueConstant.slave_password);
        slaveDataSource.setDriverClassName(ValueConstant.slave_driverClassName);

        //configuration
        slaveDataSource.setInitialSize(ValueConstant.slave_initialSize);
        slaveDataSource.setMinIdle(ValueConstant.slave_minIdle);
        slaveDataSource.setMaxActive(ValueConstant.slave_maxActive);
        slaveDataSource.setMaxWait(ValueConstant.slave_maxWait);
        slaveDataSource.setTimeBetweenEvictionRunsMillis(ValueConstant.slave_timeBetweenEvictionRunsMillis);
        slaveDataSource.setMinEvictableIdleTimeMillis(ValueConstant.slave_minEvictableIdleTimeMillis);
        slaveDataSource.setValidationQuery(ValueConstant.slave_validationQuery);
        slaveDataSource.setTestWhileIdle(ValueConstant.slave_testWhileIdle);
        slaveDataSource.setTestOnBorrow(ValueConstant.slave_testOnBorrow);
        slaveDataSource.setTestOnReturn(ValueConstant.slave_testOnReturn);
        return slaveDataSource;
    }

    @Bean
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("masterDataSource", masterDataSource());
        targetDataSources.put("slaveDataSource", slaveDataSource());
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        return dynamicDataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MAPPER_XML_PATH)));
        sqlSessionFactoryBean.setTypeAliasesPackage(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MODEL_PATH));
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper()});

        Properties properties = new Properties();
        properties.setProperty("Oracle", "oracle");
        properties.setProperty("MySQL", "mysql");
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        databaseIdProvider.setProperties(properties);
        sqlSessionFactoryBean.setDatabaseIdProvider(databaseIdProvider);
        return sqlSessionFactoryBean;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dynamicDataSource());
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
        pointcut.setExpression(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_EXPRESSION));
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }

}