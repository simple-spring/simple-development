package com.spring.simple.development.demo.service.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.mvc.req.ReqBody;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.demo.mapper.DemoDoMapperExt;
import com.spring.simple.development.demo.model.DemoDo;
import com.spring.simple.development.demo.model.DemoDoExample;
import com.spring.simple.development.demo.service.DemoBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
@IsApiService()
public class DemoBoImpl extends AbstractLavaBoImpl<DemoDo, DemoDoMapperExt, DemoDoExample> implements DemoBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(DemoDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    public ResBody queryDemo(ReqBody reqBody) {
        return null;
    }


    @Override
    public ResBody queryDemo2(ReqBody reqBody) {
        return null;
    }


}