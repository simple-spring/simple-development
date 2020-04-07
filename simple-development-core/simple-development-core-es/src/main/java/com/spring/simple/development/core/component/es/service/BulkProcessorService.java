package com.spring.simple.development.core.component.es.service;

/**
 * @author liko.wang
 * @Date 2020/4/7/007 9:04
 * @Description es实现
 **/
public interface BulkProcessorService {

    void insertById(String index, String type, String id, String jsonStr);

    void updateById(String index, String type, String id, String jsonStr);

    void deleteById(String index, String type, String id);

}
