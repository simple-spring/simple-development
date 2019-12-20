package com.chexin.simple.development.demo.service.record;


import com.chexin.simple.development.core.mvc.req.ReqBody;
import com.chexin.simple.development.core.mvc.res.ResBody;

/**
 * @author lcm
 * @date 2019/10/15 9:44
 * 描述：催收记录相关
 */
public interface CollectionRecordService {


    /**
     * 查询催收记录列表
     *
     * @param reqBody
     * @return
     */
    ResBody queryCollectionRecordList(ReqBody reqBody) throws Exception;



}
