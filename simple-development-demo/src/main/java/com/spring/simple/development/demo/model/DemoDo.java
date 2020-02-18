package com.spring.simple.development.demo.model;

import com.alibaba.lava.base.LavaDo;
import lombok.Data;

/**
 * @description MyBatis Generator 自动创建,对应数据表为：t_demo
 *
 * @author liko
 * @date   2020/02/18
 */
@Data
public class DemoDo extends LavaDo {
    /**
     * 名称
     */
    private String name;

    /**
     * 值描述
     */
    private String value;

    @Override
    public String getBoQualifiedIntfName() {
        return "com.spring.simple.development.demo.service.DemoBo";
    }
}