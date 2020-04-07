package com.spring.simple.development.core.component.es.service.impl;

import java.util.Map;


import com.spring.simple.development.core.component.es.service.ElasticSearchService;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liko.wang
 * @Date 2020/4/7/007 9:08
 * @Description es实现
 **/
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchServiceImpl.class);

    @Resource
    private TransportClient transportClient;

    @Override
    public void insertById(String index, String type, String id, String jsonStr) {
        transportClient.prepareIndex(index, type, id).setSource(jsonStr, XContentType.JSON).get();
    }

    @Override
    public void updateById(String index, String type, String id, String jsonStr) {
        transportClient.prepareUpdate(index, type, id)
                .setDoc(jsonStr, XContentType.JSON)
                .get();
    }

    @Override
    public void deleteById(String index, String type, String id) {
        transportClient.prepareDelete(index, type, id).get();
    }

    @Override
    public void batchInsert(String index, String type, Map<String, String> map) {
        BulkRequestBuilder blukRequest = transportClient.prepareBulk();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            blukRequest.add(transportClient.prepareIndex(index, type, entry.getKey()).setSource(entry.getValue(), XContentType.JSON));
            if (blukRequest.request().requests().size() % 10000 == 0) {
                blukRequest.execute().actionGet();
                blukRequest = transportClient.prepareBulk();
            }
        }
        if (blukRequest.request().requests().size() != 0) {
            blukRequest.execute().actionGet();
        }

    }
}
