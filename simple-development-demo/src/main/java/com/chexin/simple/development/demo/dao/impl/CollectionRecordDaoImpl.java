package com.chexin.simple.development.demo.dao.impl;

import com.chexin.simple.development.demo.dao.CollectionRecordDao;
import com.chexin.simple.development.demo.mapper.CollectionRecordDoMapper;
import com.chexin.simple.development.demo.model.CollectionRecordDo;
import com.chexin.simple.development.demo.model.CollectionRecordDoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liko.wang
 * @Date 2019/12/20/020 15:09
 * @Description //TODO
 **/
@Component
public class CollectionRecordDaoImpl implements CollectionRecordDao {
    @Autowired
    private CollectionRecordDoMapper collectionRecordDoMapper;

    @Override
    public List<CollectionRecordDo> queryCollectionRecordList() {
        CollectionRecordDoExample collectionRecordDoExample = new CollectionRecordDoExample();
        List<CollectionRecordDo> collectionRecordDos = collectionRecordDoMapper.selectByExample(collectionRecordDoExample);
        return collectionRecordDos;
    }
}