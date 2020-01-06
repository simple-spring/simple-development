package com.spring.simple.development.demo.service.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.component.mvc.req.ReqBody;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.demo.mapper.DemoDoMapperExt;
import com.spring.simple.development.demo.model.DemoDo;
import com.spring.simple.development.demo.model.DemoDoExample;
import com.spring.simple.development.demo.service.DemoBo;
import com.spring.simple.development.support.exception.GlobalResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@IsApiService
public class DemoBoImpl extends AbstractLavaBoImpl<DemoDo, DemoDoMapperExt, DemoDoExample> implements DemoBo {

    @Autowired
    public void setBaseMapper(DemoDoMapperExt mapper) {
        setMapper(mapper);
    }
    @Override
    public ResBody addDemo(ReqBody reqBody) throws Exception {
        DemoDo demoDo = new DemoDo();
        demoDo.setName("1234");
        demoDo.setValue("12355");
        demoDo.setValue2("123123123");
        insert(demoDo);
        return ResBody.buildSuccessResBody(null, null, GlobalResponseCode.SYS_SUCCESS);
    }
}