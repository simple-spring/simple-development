package com.spring.simple.development.demo.service;

import com.alibaba.lava.base.LavaBo;
import com.spring.simple.development.demo.model.DemoDo;
import com.spring.simple.development.demo.model.DemoDoExample;
import com.spring.simple.development.demo.vo.DemoVo;

public interface TestDemoBo extends LavaBo<DemoDo, DemoDoExample> {

    /**
     * 插入
     * @param demoVo
     */
    int insertData(DemoVo demoVo);

    /**
     * 插入
     * @param id
     */
    int deleteData(String id);

    /**
     * 查询
     * @param id
     */
    DemoVo getData(String id);
}