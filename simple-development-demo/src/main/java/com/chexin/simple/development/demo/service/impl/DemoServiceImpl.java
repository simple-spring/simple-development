package com.chexin.simple.development.demo.service.impl;


import com.chexin.simple.development.core.annotation.IsApiService;
import com.chexin.simple.development.core.annotation.NoApiMethod;
import com.chexin.simple.development.core.annotation.ValidHandler;
import com.chexin.simple.development.core.annotation.Value;
import com.chexin.simple.development.core.mvc.req.ReqBody;
import com.chexin.simple.development.core.mvc.res.ResBody;
import com.chexin.simple.development.demo.dao.DemoDao;
import com.chexin.simple.development.demo.model.DemoDo;
import com.chexin.simple.development.demo.service.DemoService;
import com.chexin.simple.development.support.GlobalResponseCode;
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
