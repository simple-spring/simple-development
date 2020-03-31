package com.spring.simple.development.core.component.cassandra;

import com.spring.simple.development.support.constant.SystemProperties;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.mapping.BasicCassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;

/**
 * @author liko.wang
 * @Date 2020/3/31/031 10:13
 * @Description //TODO
 **/
@Configuration
public class CassandraConfig {

    /**
     * @author liko.wang
     * @Date 2019/12/27/027 16:09
     * @Description 集群名称
     **/
    private String cassandraName = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_CASSANDRA_NAME);


    /**
     * @author liko.wang
     * @Date 2019/12/27/027 16:09
     * @Description 连接地址
     **/
    private String cassandraHost = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_ADDRESS);

    /**
     * @author liko.wang
     * @Date 2019/12/27/027 16:09
     * @Description 连接地址
     **/
    private Integer port = Integer.valueOf(PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_PORT)).intValue();
    /**
     * @author liko.wang
     * @Date 2019/12/27/027 16:09
     * @Description 数据名
     **/
    private String keySpacesName = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_KEY_SPACES_NAME);
    /**
     * @author liko.wang
     * @Date 2019/12/27/027 16:09
     * @Description 用户名
     **/
    private String userName = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_USERNAME);
    /**
     * @author liko.wang
     * @Date 2019/12/27/027 16:09
     * @Description 密码
     **/
    private String password = PropertyConfigurer.getProperty(SystemProperties.APPLICATION_CASSANDRA_CONFIG_PASSWORD);

    @Bean
    public CassandraClusterFactoryBean cluster() {

        CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
        cluster.setContactPoints(cassandraHost);
        cluster.setPort(port);
        cluster.setClusterName(cassandraName);
        cluster.setUsername(userName);
        cluster.setPassword(password);
        return cluster;
    }

    @Bean
    public CassandraMappingContext mappingContext() {

        BasicCassandraMappingContext mappingContext =  new BasicCassandraMappingContext();
        mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster().getObject(), keySpacesName));

        return mappingContext;
    }

    @Bean
    public CassandraConverter converter() {
        return new MappingCassandraConverter(mappingContext());
    }

    @Bean
    public CassandraSessionFactoryBean session() throws Exception {

        CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
        session.setCluster(cluster().getObject());
        session.setKeyspaceName(keySpacesName);
        session.setConverter(converter());
        session.setSchemaAction(SchemaAction.NONE);

        return session;
    }

    @Bean
    public CassandraOperations cassandraTemplate() throws Exception {
        return new CassandraTemplate(session().getObject());
    }

}