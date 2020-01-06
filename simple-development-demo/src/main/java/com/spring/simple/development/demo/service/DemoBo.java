package com.spring.simple.development.demo.service;

import com.alibaba.lava.base.LavaBo;
import com.spring.simple.development.core.component.mvc.req.ReqBody;
import com.spring.simple.development.core.component.mvc.res.ResBody;
import com.spring.simple.development.demo.model.DemoDo;
import com.spring.simple.development.demo.model.DemoDoExample;

public interface DemoBo extends LavaBo<DemoDo, DemoDoExample> {
    /**
     * 添加账户
     * @param reqBody
     * @return
     */
    ResBody addDemo(ReqBody reqBody) throws Exception;
}