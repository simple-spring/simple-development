package com.spring.simple.development.demo.service.impl;


import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.annotation.base.Value;
import com.spring.simple.development.core.component.mvc.req.ReqBody;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.demo.dao.DemoDao;
import com.spring.simple.development.demo.model.DemoDo;
import com.spring.simple.development.demo.service.DemoService;
import com.spring.simple.development.support.GlobalResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author lcm
 * @date 2019/10/15 9:48
 */
@IsApiService
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoDao demoDao;
    @Value(value = "spring.base.package")
    private String pageName;

    @Override
    public ResBody querydemoDoList(ReqBody reqBody) {
        List<DemoDo> demoDoList = demoDao.queryList();
        return ResBody.buildSuccessResBody(demoDoList, null, GlobalResponseCode.SYS_SUCCESS);
    }

    @Override
    @NoApiMethod
    public ResBody insertDemoDo(ReqBody reqBody) throws Exception {
        DemoDo demoDo = new DemoDo();
        demoDo.setName("test");
        demoDo.setValue("test");
        demoDao.insertDemoDo(demoDo);
        throw new RuntimeException();

    }
}
