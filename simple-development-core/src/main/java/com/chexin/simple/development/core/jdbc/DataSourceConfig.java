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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author liko.wang
 * @Date 2019/12/19/019 15:25
 * @Description mybatis config
 **/
@Configuration
@ImportResource("classpath:/aop-config.xml")
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

}