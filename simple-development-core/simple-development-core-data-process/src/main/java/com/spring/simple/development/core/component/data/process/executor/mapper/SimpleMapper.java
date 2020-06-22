package com.spring.simple.development.core.component.data.process.executor.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Administrator
 */
public interface SimpleMapper<T> {
    /**
     * 执行查询sql
     *
     * @param sql
     * @param resultType
     * @return
     */
    @Select("${sql}")
    List<T> executorSql(@Param("sql") String sql, @Param("resultType") T resultType);

    /**
     * 执行更新and删除sql
     *
     * @param sql
     * @return
     */
    @Update("${sql}")
    Integer updateSql(@Param("sql") String sql);

    /**
     * 执行新增sql
     *
     * @param sql
     * @return
     */
    @Insert("${sql}")
    Integer insertSql(@Param("sql") String sql);

}
