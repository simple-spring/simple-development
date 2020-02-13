package com.spring.simple.development.demo.service.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.annotation.base.swagger.Api;
import com.spring.simple.development.core.annotation.base.swagger.ApiImplicitParam;
import com.spring.simple.development.core.annotation.base.swagger.ApiOperation;
import com.spring.simple.development.demo.mapper.DemoDoMapperExt;
import com.spring.simple.development.demo.model.DemoDo;
import com.spring.simple.development.demo.model.DemoDoExample;
import com.spring.simple.development.demo.service.DemoBo;
import com.spring.simple.development.demo.support.BaseSupport;
import com.spring.simple.development.demo.vo.DemoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@IsApiService
@Api(tags = "用户相关")
public class DemoBoImpl extends AbstractLavaBoImpl<DemoDo, DemoDoMapperExt, DemoDoExample> implements DemoBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(DemoDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    @ApiOperation(value = "插入", notes = "插入一亿个订单")
    @ApiImplicitParam(name = "demoVo", description = "用户vo")
    public void insertData(DemoVo demoVo) {
        DemoDo demoDo = baseSupport.objectCopy(demoVo, DemoDo.class);
        insert(demoDo);
    }

    @Override
    @ApiOperation(value = "查询", notes = "查询一亿个订单")
    @ApiImplicitParam(name = "demoVo", description = "用户vo", resultDataType = DemoVo.class)
    public DemoVo getData(Long id) {
        DemoDo demoDo = selectByPrimaryKey(id);
        return baseSupport.objectCopy(demoDo, DemoVo.class);
    }
}