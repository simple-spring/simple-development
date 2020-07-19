package com.spring.simple.development.demo.service;

import com.alibaba.lava.base.LavaBo;
import com.spring.simple.development.demo.model.DemoDo;
import com.spring.simple.development.demo.model.DemoDoExample;
import com.spring.simple.development.demo.vo.DemoVo;

/**
 * @author luke
 */
public interface TestDemoBo extends LavaBo<DemoDo, DemoDoExample> {

    /**
     * 增
     *
     * @param demoVo
     */
    void insertData(DemoVo demoVo);

    /**
     * 删
     *
     * @param id
     */
    void deleteData(Long id);

    /**
     * 改
     *
     * @param demoVo
     */
    void modifyData(DemoVo demoVo);

    /**
     * 查
     *
     * @param id
     * @return
     */
    DemoVo getData(Long id);
}