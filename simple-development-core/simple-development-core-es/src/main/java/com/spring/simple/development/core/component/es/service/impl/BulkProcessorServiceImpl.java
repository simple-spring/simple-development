package com.spring.simple.development.core.component.es.service.impl;

import com.spring.simple.development.core.component.es.service.BulkProcessorService;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liko.wang
 * @Date 2020/4/7/007 9:06
 * @Description es实现
 **/
@Service
public class BulkProcessorServiceImpl implements BulkProcessorService {

    @Autowired
    BulkProcessor bulkProcessor;

    @Override
    public void insertById(String index, String type, String id, String jsonStr) {
        bulkProcessor.add(new IndexRequest(index, type,id)
                .source(jsonStr, XContentType.JSON));
    }

    @Override
    public void updateById(String index, String type, String id, String jsonStr) {
        bulkProcessor.add(new UpdateRequest(index, type,id)
                .doc(jsonStr, XContentType.JSON));
    }

    @Override
    public void deleteById(String index, String type, String id) {
        bulkProcessor.add(new DeleteRequest(index, type,id));
    }
}
