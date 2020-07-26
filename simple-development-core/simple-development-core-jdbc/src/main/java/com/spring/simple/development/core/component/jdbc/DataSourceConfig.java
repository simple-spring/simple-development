package com.spring.simple.development.core.component.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.lava.privilege.PrivilegeInfo;
import com.alibaba.lava.util.SpringUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageHelper;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.baseconfig.datasource.DynamicDataSource;
import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.constant.ValueConstant;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
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

    @Bean
    public SpringUtil getSpringUtil() {
        return new SpringUtil();
    }

    /**
     * lava用户对象
     *
     * @return
     */
    @Bean(name = "lavaPvgInfo")
    public PrivilegeInfo privilegeInfo() {
        PrivilegeInfo privilegeInfo = new PrivilegeInfo();
        privilegeInfo.setAesKey("acl-auth-support-dubbo-service");
        return new PrivilegeInfo();
    }

    /**
     * 创建全局配置
     *
     * @return
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        // 默认为自增
        dbConfig.setIdType(IdType.AUTO);
        // 全局的表前缀策略配置
        dbConfig.setTablePrefix("t_");
        globalConfig.setDbConfig(dbConfig);
        return globalConfig;
    }

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
        dynamicDataSource.afterPropertiesSet();
        return dynamicDataSource;
    }

    @Bean
    @IsApiService
    public MapperScannerConfigurer getMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MAPPER_PATH));
        return mapperScannerConfigurer;
    }

    @Bean(value = "sqlSessionFactory")
    public MybatisSqlSessionFactoryBean getMybatisSqlSessionFactoryBean() throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();

        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        sqlSessionFactoryBean.setMapperLocations(resourcePatternResolver.getResources(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MAPPER_XML_PATH)));
        sqlSessionFactoryBean.setTypeAliasesPackage(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_MODEL_PATH));

        // 是否打开数据转换工具
        boolean isEnableDataPrecess = Boolean.parseBoolean(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_MYBATIS_CONFIG_DATA_PROCESS));
        if (isEnableDataPrecess) {
            sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper(), paginationInterceptor(), new ResultTypeInterceptor()});
        } else {
            sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper(), paginationInterceptor()});
        }
        Properties properties = new Properties();
        properties.setProperty("Oracle", "oracle");
        properties.setProperty("MySQL", "mysql");
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        databaseIdProvider.setProperties(properties);
        sqlSessionFactoryBean.setDatabaseIdProvider(databaseIdProvider);
        // 全局配置
        sqlSessionFactoryBean.setGlobalConfig(globalConfig());
        return sqlSessionFactoryBean;
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dynamicDataSource());
        return transactionManager;
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
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