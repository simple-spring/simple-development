package com.spring.simple.development.demo.service.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.data.process.hander.DataProcessHelper;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.demo.mapper.AccountDoMapperExt;
import com.spring.simple.development.demo.model.AccountDo;
import com.spring.simple.development.demo.model.AccountDoExample;
import com.spring.simple.development.demo.service.AccountBo;
import com.spring.simple.development.demo.vo.AccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
@IsApiService
public class AccountBoImpl extends AbstractLavaBoImpl<AccountDo, AccountDoMapperExt, AccountDoExample> implements AccountBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(AccountDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Autowired
    private BaseSupport baseSupport;

    @Override
    public List<AccountVo> getData(AccountVo accountVo) {
        List<AccountDo> executor = (List<AccountDo>) DataProcessHelper.executor(accountVo.getClass());
        return baseSupport.listCopy(executor, AccountVo.class);
    }
}