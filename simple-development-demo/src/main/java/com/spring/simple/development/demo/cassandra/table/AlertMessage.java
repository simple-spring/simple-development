//package com.spring.simple.development.demo.cassandra.table;
//
//import lombok.Data;
//import org.springframework.data.cassandra.core.mapping.PrimaryKey;
//import org.springframework.data.cassandra.core.mapping.Table;
//
///**
// * @author liko.wang
// * @Date 2020/3/31/031 12:37
// * @Description //告警消息表
// **/
//@Data
//@Table(value = "alertmessage")
//public class AlertMessage {
//    /**
//     * @author liko.wang
//     * @Date 2020/3/31/031 12:42
//     * @param null
//     * @return
//     * @Description 主键ID
//     **/
//    @PrimaryKey
//    private String id;
//    /**
//     * @author liko.wang
//     * @Date 2020/3/31/031 12:42
//     * @Description 应用Code
//     **/
//    private String applicationcode;
//    /**
//     * @author liko.wang
//     * @Date 2020/3/31/031 12:43
//     * @return
//     * @Description 应用名称
//     **/
//    private String applicationname;
//    /**
//     * @author liko.wang
//     * @Date 2020/3/31/031 12:43
//     * @Description 应用类型(simple / android)
//     **/
//    private String applicationtype;
//    /**
//     * @author liko.wang
//     * @Date 2020/3/31/031 12:43
//     * @Description 创建时间
//     **/
//    private String createtime;
//    /**
//     * @author liko.wang
//     * @Date 2020/3/31/031 12:43
//     * @Description 是否推送（y/n）
//     **/
//    private String ispush;
//    /**
//     * @author liko.wang
//     * @Date 2020/3/31/031 12:43
//     * @Description 告警消息
//     **/
//    private String message;
//    /**
//     * @author liko.wang
//     * @Date 2020/3/31/031 12:43
//     * @Description 消息级别（低，中，高）
//     **/
//    private String messagetype;
//    /**
//     * @author liko.wang
//     * @Date 2020/3/31/031 12:43
//     * @Description 推送类型(1,系统实现，2，自定义实现)
//     **/
//    private String pushtype;
//
//}