package com.spring.simple.development.demo.dao;

import com.spring.simple.development.demo.model.DemoDo;

import java.util.List;

/**
 * @author liko.wang
 * @Date 2019/12/20/020 15:08
 * @Description //TODO
 **/
public interface DemoDao {
    /**
     * @author liko.wang
     * @Date 2019/12/20/020 15:09
     * @param
     * @return java.util.List<com.spring.simple.development.demo.model.CollectionRecordDo>
     * @Description //获取列表
     **/
    List<DemoDo> queryList();

    /**
     * 插入
     * @param demoDo
     */
    void insertDemoDo(DemoDo demoDo);
}