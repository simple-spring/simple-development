package com.chexin.simple.development.demo.dao;

import com.chexin.simple.development.demo.model.CollectionRecordDo;

import java.util.List;

/**
 * @author liko.wang
 * @Date 2019/12/20/020 15:08
 * @Description //TODO
 **/
public interface CollectionRecordDao {
    /**
     * @author liko.wang
     * @Date 2019/12/20/020 15:09
     * @param
     * @return java.util.List<com.chexin.simple.development.demo.model.CollectionRecordDo>
     * @Description //获取列表
     **/
    List<CollectionRecordDo> queryCollectionRecordList();
}