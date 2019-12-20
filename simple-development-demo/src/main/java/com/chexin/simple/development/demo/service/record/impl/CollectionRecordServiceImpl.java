package com.chexin.simple.development.demo.service.record.impl;


import com.chexin.simple.development.core.annotation.IsApiService;
import com.chexin.simple.development.core.mvc.req.ReqBody;
import com.chexin.simple.development.core.mvc.res.ResBody;
import com.chexin.simple.development.demo.dao.CollectionRecordDao;
import com.chexin.simple.development.demo.service.record.CollectionRecordService;
import com.chexin.simple.development.support.GlobalResponseCode;
import com.chexin.simple.development.demo.model.CollectionRecordDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author lcm
 * @date 2019/10/15 9:48
 */
@IsApiService
@Service
public class CollectionRecordServiceImpl implements CollectionRecordService {

    @Autowired
    private CollectionRecordDao collectionRecordDao;

    @Override
    public ResBody queryCollectionRecordList(ReqBody reqBody){
        List<CollectionRecordDo> collectionRecordDos = collectionRecordDao.queryCollectionRecordList();
        return ResBody.buildSuccessResBody(collectionRecordDos, null, GlobalResponseCode.SYS_SUCCESS);
    }

}
