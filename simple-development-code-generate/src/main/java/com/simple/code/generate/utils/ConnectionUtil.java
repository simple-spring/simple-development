package com.simple.code.generate.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author luke
 */
public class ConnectionUtil {
    private final static String DRIVER = "com.mysql.jdbc.Driver";


    /**
     * 初始化数据库连接
     *
     * @param database 数据库名
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public static Connection initConnection(String ip, String port, String database, String username, String password) {
        try {
            Class.forName(DRIVER);
            String url = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?useUnicode=true&characterEncoding=UTF-8";
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("获取连接失败");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("获取连接失败");
        }
    }

    /**
     * 获取表结构数据
     *
     * @param dataBaseName 库名
     * @return 包含表结构数据的列表
     */
    public static List<String> getMetaData(String ip, String port, String username, String password, String dataBaseName) throws SQLException {

        Connection connection = initConnection(ip, port, dataBaseName, username, password);
        List<String> tables = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            String[] types = { "TABLE" };
            resultSet = connection.getMetaData().getTables(dataBaseName, null, "%", types);;
            while (resultSet.next()) {
                tables.add(resultSet.getString(3));
            }
            return tables;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("关闭连接失败");
            }
        }
    }
}
