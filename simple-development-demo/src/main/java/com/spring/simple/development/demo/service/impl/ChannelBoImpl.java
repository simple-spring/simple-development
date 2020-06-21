package com.spring.simple.development.demo.service.impl;

import com.alibaba.lava.base.AbstractLavaBoImpl;
import com.spring.simple.development.core.annotation.base.IsApiService;
import com.spring.simple.development.core.annotation.base.NoApiMethod;
import com.spring.simple.development.core.component.data.process.hander.DataProcessHelper;
import com.spring.simple.development.core.component.mvc.BaseSupport;
import com.spring.simple.development.demo.mapper.ChannelDoMapperExt;
import com.spring.simple.development.demo.model.ChannelDo;
import com.spring.simple.development.demo.model.ChannelDoExample;
import com.spring.simple.development.demo.service.ChannelBo;
import com.spring.simple.development.demo.vo.ChannelVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@IsApiService
public class ChannelBoImpl extends AbstractLavaBoImpl<ChannelDo, ChannelDoMapperExt, ChannelDoExample> implements ChannelBo {

    @Autowired
    @NoApiMethod
    public void setBaseMapper(ChannelDoMapperExt mapper) {
        setMapper(mapper);
    }

    @Autowired
    private BaseSupport baseSupport;

    @Override
    public List<ChannelVo> getData(ChannelVo channelVo) {
        List<ChannelDo> executor = (List<ChannelDo>) DataProcessHelper.executor(channelVo.getClass());
        return baseSupport.listCopy(executor, ChannelVo.class);
    }

    @Override
    public ChannelVo getOneData() {
        ChannelDoExample channelDoExample = new ChannelDoExample();
        ChannelDo channelDo = this.mapper.selectByPrimaryKey(1L);
        this.delete(1L);
        this.update(channelDo);
        return baseSupport.objectCopy(this.mapper.selectByPrimaryKey(1L), ChannelVo.class);
    }
}