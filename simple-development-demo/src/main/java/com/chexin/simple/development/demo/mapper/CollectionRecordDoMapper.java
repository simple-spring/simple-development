package com.chexin.simple.development.demo.mapper;

import com.chexin.simple.development.demo.model.CollectionRecordDo;
import com.chexin.simple.development.demo.model.CollectionRecordDoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CollectionRecordDoMapper {
    long countByExample(CollectionRecordDoExample example);

    int deleteByExample(CollectionRecordDoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CollectionRecordDo record);

    int insertSelective(CollectionRecordDo record);

    List<CollectionRecordDo> selectByExample(CollectionRecordDoExample example);

    CollectionRecordDo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CollectionRecordDo record, @Param("example") CollectionRecordDoExample example);

    int updateByExample(@Param("record") CollectionRecordDo record, @Param("example") CollectionRecordDoExample example);

    int updateByPrimaryKeySelective(CollectionRecordDo record);

    int updateByPrimaryKey(CollectionRecordDo record);
}