package com.spring.simple.development.demo.mapper;

import com.alibaba.lava.base.LavaMapper;
import com.spring.simple.development.demo.model.DemoDo;
import com.spring.simple.development.demo.model.DemoDoExample;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.Map;

public interface DemoDoMapperExt extends LavaMapper<DemoDo, DemoDoExample> {
    int insertSelective(T var1);

    int deleteByPrimaryKey(T var1);

    int countByExample(DemoDoExample var1);

    List<DemoDo> selectByExample(DemoDoExample var1);

    int updateByExampleSelective(@Param("record") T var1, @Param("example") DemoDoExample var2);

    int updateByExampleSelective(Map<String, Object> var1);

    int updateByExample(@Param("record") T var1, @Param("example") DemoDoExample var2);

    int updateByPrimaryKeySelective(T var1);

    DemoDo selectByPrimaryKey(Long var1);
}