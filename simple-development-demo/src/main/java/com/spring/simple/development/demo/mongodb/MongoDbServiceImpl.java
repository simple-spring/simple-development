package com.spring.simple.development.demo.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.spring.simple.development.core.component.mongodb.client.MongoDBClient;
import org.bson.Document;
import org.springframework.stereotype.Service;

/**
 * @author hjs
 * @desc: 测试mongodb
 * @date: 2020/6/16
 */
@Service
public class MongoDbServiceImpl implements MongoDbService {

    @Override
    public void testMongoDb() {
        // 获取客户端
        MongoClient mongoClient = MongoDBClient.getMongoClient();
        // 指定库
        MongoDatabase sfs = mongoClient.getDatabase("demo");
        // 集合
        MongoCollection<Document> coll = sfs.getCollection("demo");
        //查询所有
        try (MongoCursor<Document> cursor = coll.find().iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }

    }
}
