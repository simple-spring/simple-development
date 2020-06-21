package com.spring.simple.development.core.component.data.process.executor.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
public interface SimpleMapper {
    /**
     * 执行sql
     *
     * @param sql
     * @return
     */
    @Select("${sql}")
    List<Object> executorSql(@Param("sql") String sql, @Param("resultType") Class resultType);
}
