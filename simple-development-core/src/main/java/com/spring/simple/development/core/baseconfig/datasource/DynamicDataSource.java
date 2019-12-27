package com.spring.simple.development.core.baseconfig.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * desc:    动态获取数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 数据源-主库
     */
    public static final String master = "masterDataSource";
    /**
     * 数据源-从库
     */
    public static final String slave = "slaveDataSource";

    @Override
    protected Object determineCurrentLookupKey() {
        // 从自定义的位置获取数据源标识
        return DynamicDataSourceHolder.getDataSource();
    }
}
