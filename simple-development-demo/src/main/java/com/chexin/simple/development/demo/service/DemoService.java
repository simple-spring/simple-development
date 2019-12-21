package com.chexin.simple.development.demo.service;


import com.chexin.simple.development.core.mvc.req.ReqBody;
import com.chexin.simple.development.core.mvc.res.ResBody;

/**
 * demo
 */
public interface DemoService {


    /**
     * 查询列表
     *
     * @param reqBody
     * @return
     */
    ResBody querydemoDoList(ReqBody reqBody) throws Exception;

    /**
     * 插入
     * @param reqBody
     * @return
     * @throws Exception
     */
    ResBody insertDemoDo(ReqBody reqBody) throws Exception;



}
