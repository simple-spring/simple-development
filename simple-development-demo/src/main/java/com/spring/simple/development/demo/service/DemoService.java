package com.spring.simple.development.demo.service;

import com.spring.simple.development.demo.model.DemoDo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.simple.development.demo.vo.DemoVo;

/**
 * <p>
 * demo 服务类
 * </p>
 *
 * @author luke
 * @since 2020-07-27
 */
public interface DemoService extends IService<DemoDo> {

    void addDemoVo(DemoVo demoVo);
}
