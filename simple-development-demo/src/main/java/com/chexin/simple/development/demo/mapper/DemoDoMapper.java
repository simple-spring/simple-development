package com.spring.simple.development.demo.mapper;

import com.spring.simple.development.demo.model.DemoDo;
import com.spring.simple.development.demo.model.DemoDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DemoDoMapper {
    long countByExample(DemoDoExample example);

    int deleteByExample(DemoDoExample example);

    int deleteByPrimaryKey(String name);

    int insert(DemoDo record);

    int insertSelective(DemoDo record);

    List<DemoDo> selectByExample(DemoDoExample example);

    DemoDo selectByPrimaryKey(String name);

    int updateByExampleSelective(@Param("record") DemoDo record, @Param("example") DemoDoExample example);

    int updateByExample(@Param("record") DemoDo record, @Param("example") DemoDoExample example);

    int updateByPrimaryKeySelective(DemoDo record);

    int updateByPrimaryKey(DemoDo record);
}