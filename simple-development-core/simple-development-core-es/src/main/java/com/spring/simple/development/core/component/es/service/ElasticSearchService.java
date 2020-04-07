package com.spring.simple.development.core.component.es.service;

import java.util.Map;

/**
 * @author liko.wang
 * @Date 2020/4/7/007 9:05
 * @Description es实现
 **/
public interface ElasticSearchService {
    void insertById(String index, String type, String id, String jsonStr);

    void updateById(String index, String type, String id, String jsonStr);

    void deleteById(String index, String type, String id);

    void batchInsert(String index, String type, Map<String, String> map);
}
