package com.spring.simple.development.demo.service;

import com.alibaba.lava.base.LavaBo;
import com.spring.simple.development.demo.model.AccountDo;
import com.spring.simple.development.demo.model.AccountDoExample;
import com.spring.simple.development.demo.vo.AccountVo;

import java.util.List;

public interface AccountBo extends LavaBo<AccountDo, AccountDoExample> {

    List<AccountVo> getData(AccountVo accountVo);
}