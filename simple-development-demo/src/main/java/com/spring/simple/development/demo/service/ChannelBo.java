package com.spring.simple.development.demo.service;

import com.alibaba.lava.base.LavaBo;
import com.spring.simple.development.demo.model.ChannelDo;
import com.spring.simple.development.demo.model.ChannelDoExample;
import com.spring.simple.development.demo.vo.ChannelVo;

import java.util.List;

public interface ChannelBo extends LavaBo<ChannelDo, ChannelDoExample> {

    List<ChannelVo> getData(ChannelVo channelVo);

    ChannelVo getOneData();
}