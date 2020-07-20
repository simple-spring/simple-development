package com.spring.simple.development.demo.cassandra.table;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

/**
 * @author liko.wang
 * @Date 2020/3/31/031 12:37
 * //告警消息表
 **/
@Data
@Table(value = "demo")
public class DemoCassandraDo {
    /**
     * 主键ID
     **/
    @PrimaryKey
    private String id;
    /**
     * 应用Code
     **/
    private String applicationCode;
    /**
     * 应用名称
     **/
    private String applicationName;
    /**
     * 应用类型(simple / android)
     **/
    private String appliationType;
    /**
     * 创建时间
     **/
    private String createTime;
    /**
     * 是否推送（y/n）
     **/
    private String isPush;
    /**
     * 告警消息
     **/
    private String message;
    /**
     * 消息级别（低，中，高）
     **/
    private String messageType;
    /**
     * 推送类型(1,系统实现，2，自定义实现)
     **/
    private String pushType;

}