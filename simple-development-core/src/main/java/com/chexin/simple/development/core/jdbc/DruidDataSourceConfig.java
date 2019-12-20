package com.chexin.simple.development.core.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.chexin.simple.development.core.constant.ValueConstant;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 15:25
 * @Description //TODO
 **/
@Configuration
//加上这个注解，使得支持事务
@EnableTransactionManagement
@MapperScan(basePackages = "com.chexin.simple.development.demo.mapper")
public class DruidDataSourceConfig {
    private final static Logger LOG = Logger.getLogger(DruidDataSourceConfig.class);

    @Bean
    public DataSource dataSource() {
        LOG.info("Initialize the data source...");
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
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath*:mybatis/*/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.chexin.simple.development.demo.model");//别名，让*Mpper.xml实体类映射可以不加上具体包名
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper()});
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer MapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.chexin.simple.development.demo.mapper");
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
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    @Bean(name = "transactionInterceptor")
    public TransactionInterceptor interceptor() {
        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionManager(dataSourceTransactionManager());

        Properties transactionAttributes = new Properties();
        transactionAttributes.setProperty("save*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("del*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("update*", "PROPAGATION_REQUIRED");
        transactionAttributes.setProperty("get*", "PROPAGATION_REQUIRED,readOnly");
        transactionAttributes.setProperty("find*", "PROPAGATION_REQUIRED,readOnly");
        transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED");

        interceptor.setTransactionAttributes(transactionAttributes);
        return interceptor;
    }
}