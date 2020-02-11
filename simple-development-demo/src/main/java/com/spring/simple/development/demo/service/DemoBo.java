package com.spring.simple.development.demo.service;

import com.alibaba.lava.base.LavaBo;
import com.spring.simple.development.demo.model.DemoDo;
import com.spring.simple.development.demo.model.DemoDoExample;
import com.spring.simple.development.demo.vo.DemoVo;

public interface DemoBo extends LavaBo<DemoDo, DemoDoExample> {

    /**
     * 查询
     * @param demoVo
     */
    void getData(DemoVo demoVo);
}