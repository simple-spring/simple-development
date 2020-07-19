package com.spring.simple.development.demo.service.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.alibaba.lava.privilege.PrivilegeInfo;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.annotation.base.swagger.Api;
import com.spring.simple.development.core.annotation.base.swagger.ApiImplicitParam;
import com.spring.simple.development.core.annotation.base.swagger.ApiOperation;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.demo.mapper.DemoDoMapperExt;
import com.spring.simple.development.demo.model.DemoDo;
import com.spring.simple.development.demo.model.DemoDoExample;
import com.spring.simple.development.demo.service.TestDemoBo;
import com.spring.simple.development.demo.vo.DemoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

@IsApiService(isLogin = false)
@Api(tags = "用户相关11111")
public class TestDemoBoImpl extends AbstractLavaBoImpl<DemoDo, DemoDoMapperExt, DemoDoExample> implements TestDemoBo {

    @Autowired
    private BaseSupport baseSupport;
    @Autowired
    PrivilegeInfo privilegeInfo;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(DemoDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    @CacheEvict(value = "test", key = "#demoVo.id", condition = "#demoVo != null")
    @ApiOperation(value = "插入", notes = "插入一亿个订单")
    @ApiImplicitParam(name = "demoVo", description = "用户vo")
    public void insertData(DemoVo demoVo) {
        DemoDo demoDo = baseSupport.objectCopy(demoVo, DemoDo.class);
        insert(demoDo);
    }

    @Override
    @CacheEvict(value = "test", key = "#id", condition = "#id != null")
    @ApiOperation(value = "删除", notes = "删除一亿个订单")
    @ValidHandler(key = "demoVo", value = DemoVo.class, isReqBody = false)
    public void deleteData(Long id) {
        delete(id);
    }

    @Override
    public void modifyData(DemoVo demoVo) {

    }

    @Override
    @Cacheable(value = "test", key = "#id", condition = "#id != null",unless="#result == null")
    @ApiOperation(value = "查询", notes = "查询一亿个订单")
    @ApiImplicitParam(name = "demoVo", description = "用户vo", resultDataType = DemoVo.class)
    public DemoVo getData(Long id) {
        System.out.println(privilegeInfo);
        DemoDo demoDo = selectByPrimaryKey(id);
        return baseSupport.objectCopy(demoDo, DemoVo.class);
    }
}