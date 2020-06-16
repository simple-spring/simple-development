package com.spring.simple.development.core.component.mongodb.config;

import com.mongodb.*;
import com.spring.simple.development.core.component.mongodb.client.MongoDBClient;
import com.spring.simple.development.support.properties.PropertyConfigurer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @desc: mongoDB系统配置
 * @auth: hjs
 * @date: 2020/6/15 0015
 */
public class MongoDBConfig {

    private String host = PropertyConfigurer.getProperty("spring.simple.mongodb.host");
    private String port = PropertyConfigurer.getProperty("spring.simple.mongodb.port");
    private String userName = PropertyConfigurer.getProperty("spring.simple.mongodb.userName");
    private String pwd = PropertyConfigurer.getProperty("spring.simple.mongodb.pwd");

    private static String connectionsPerHost = PropertyConfigurer.getProperty("spring.simple.mongodb.connectionsPerHost");
    private static String connectTimeout = PropertyConfigurer.getProperty("spring.simple.mongodb.connectTimeout");

    public MongoDBConfig() {
        System.out.println("MongoDB initialized...");
        Assert.notNull(host, "MongoDB host is empty");
        Assert.notNull(port, "MongoDB post is empty");
        Assert.notNull(userName, "MongoDB userName is empty");
        Assert.notNull(pwd, "MongoDB pwd is empty");
        init(host, port, userName, pwd);
        System.out.println("MongoDB initialized successfully");
    }

    public synchronized static void init(String host, String port, String userName, String pwd) {
        // 设置地址端口
        ServerAddress serverAddress = new ServerAddress(host, Integer.valueOf(port));
        // 登陆信息验证
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(userName, "admin", pwd.toCharArray());
        // 连接池参数配置
        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        if (!StringUtils.isEmpty(connectionsPerHost)) {
            // 与目标数据库能够建立的最大connection数量,默认100,设置每个主机的最大连接数。
            build.connectionsPerHost(Integer.valueOf(connectionsPerHost));
        }
        if (!StringUtils.isEmpty(connectTimeout)) {
            // 与数据库建立连接的timeout设置为1分钟单位毫秒，默认10秒钟
            build.connectTimeout(Integer.valueOf(connectTimeout));
        }
        MongoClientOptions myOptions = build.build();
        // 获取客户端
        MongoClient mongoClient = new MongoClient(serverAddress, mongoCredential, myOptions);
        MongoDBClient.setMongoClient(mongoClient);
    }


}
