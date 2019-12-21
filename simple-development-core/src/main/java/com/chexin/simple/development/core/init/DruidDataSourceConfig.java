package com.chexin.simple.development.core.init;

import com.alibaba.druid.pool.DruidDataSource;
import com.chexin.simple.development.core.constant.ValueConstant;
import com.chexin.simple.development.support.properties.PropertyConfigurer;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
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
public class DruidDataSourceConfig {
    private final static Logger logger = Logger.getLogger(DruidDataSourceConfig.class);

    @Bean
    public DataSource dataSource() {
        logger.info("Initialize the data source...");
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
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources(PropertyConfigurer.getBaseProperty("mybatis.mapper.path")));
        sqlSessionFactoryBean.setTypeAliasesPackage(PropertyConfigurer.getBaseProperty("model.package.name"));
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper()});
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer MapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(PropertyConfigurer.getBaseProperty("mapper.package.name"));
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        return mapperScannerConfigurer;
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

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean(name = "transactionInterceptor")
    public TransactionInterceptor interceptor() {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(transactionManager());

        Properties transactionAttributes = new Properties();
        // 增删改查
        transactionAttributes.setProperty("save*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("insert*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("add*", "PROPAGATION_REQUIRED");

        transactionAttributes.setProperty("del*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("remove*", "PROPAGATION_REQUIRED");

        transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("modify*", "PROPAGATION_REQUIRED");

        transactionAttributes.setProperty("get*", "readOnly");
        transactionAttributes.setProperty("find*", "readOnly");
        transactionAttributes.setProperty("query*", "PROPAGATION_REQUIRED,readOnly");
        transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED");

        transactionInterceptor.setTransactionAttributes(transactionAttributes);
        return transactionInterceptor;
    }
}