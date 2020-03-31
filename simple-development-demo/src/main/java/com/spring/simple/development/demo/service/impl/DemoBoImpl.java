package com.spring.simple.development.demo.service.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.spring.simple.development.core.annotation.base.Idempotent;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.annotation.base.ValidHandler;
import com.spring.simple.development.core.annotation.base.swagger.Api;
import com.spring.simple.development.core.annotation.base.swagger.ApiImplicitParam;
import com.spring.simple.development.core.annotation.base.swagger.ApiOperation;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.demo.cassandra.repository.AlertMessageRepository;
import com.spring.simple.development.demo.cassandra.table.AlertMessage;
import com.spring.simple.development.demo.mapper.DemoDoMapperExt;
import com.spring.simple.development.demo.model.DemoDo;
import com.spring.simple.development.demo.model.DemoDoExample;
import com.spring.simple.development.demo.service.DemoBo;
import com.spring.simple.development.demo.vo.DemoVo;
import com.spring.simple.development.support.exception.GlobalException;
import com.spring.simple.development.support.utils.PrimaryKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.spring.simple.development.support.exception.ResponseCode.RES_PARAM_IS_EMPTY;

@Service
@IsApiService
@Api(tags = "用户相关")
public class DemoBoImpl extends AbstractLavaBoImpl<DemoDo, DemoDoMapperExt, DemoDoExample> implements DemoBo {

    @Autowired
    private BaseSupport baseSupport;

    @Autowired
    private AlertMessageRepository alertMessageRepository;

    @Autowired
    @NoApiMethod
    public void setBaseMapper(DemoDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Override
    @ApiOperation(value = "插入", notes = "插入一亿个订单")
    @ApiImplicitParam(name = "demoVo", description = "用户vo")
    //@ValidHandler(key = "demoVo",value = DemoVo.class,isReqBody = false)
    public void insertData(DemoVo demoVo){
        AlertMessage alertMessage = new AlertMessage();
        alertMessage.setId(PrimaryKeyGenerator.getInstance().nextId().toString());
        alertMessage.setApplicationcode("123");
        alertMessage.setApplicationname("123");
        alertMessage.setApplicationtype("1");
        alertMessage.setCreatetime("2020-03-31 12:00:00");
        alertMessage.setIspush("1");
        alertMessage.setMessage("alert data");
        alertMessage.setPushtype("2");
        alertMessageRepository.save(alertMessage);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DemoDo demoDo = baseSupport.objectCopy(demoVo, DemoDo.class);
        insert(demoDo);
    }

    @Override
    @Idempotent(value = "getData")
    @ApiOperation(value = "查询", notes = "查询一亿个订单")
    @ApiImplicitParam(name = "demoVo", description = "用户vo", resultDataType = DemoVo.class)
    public DemoVo getData() {
        throw new GlobalException(RES_PARAM_IS_EMPTY, "User code can not be empty");// 用户名不能为空
        //DemoDo demoDo = selectByPrimaryKey(1L);
        // return baseSupport.objectCopy(demoDo, DemoVo.class);
    }
}