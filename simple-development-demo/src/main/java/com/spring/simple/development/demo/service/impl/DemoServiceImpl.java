package com.spring.simple.development.demo.service.impl;

import com.spring.simple.development.demo.model.DemoDo;
import com.spring.simple.development.demo.mapper.DemoMapper;
import com.spring.simple.development.demo.service.DemoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.simple.development.demo.vo.DemoVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * demo 服务实现类
 * </p>
 *
 * @author luke
 * @since 2020-07-27
 */
@Service
public class DemoServiceImpl extends ServiceImpl<DemoMapper, DemoDo> implements DemoService {

    @Override
    public void addDemoVo(DemoVo demoVo) {

    }
}
