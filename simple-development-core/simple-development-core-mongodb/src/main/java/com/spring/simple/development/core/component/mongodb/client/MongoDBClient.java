package com.spring.simple.development.core.component.mongodb.client;

import com.mongodb.MongoClient;

/**
 * @desc: MongoDB客户端
 * @auth: hjs
 */
public class MongoDBClient {
    private static MongoClient client;
    public static MongoClient getMongoClient() {
        return client;
    }
    public static void setMongoClient(MongoClient mongoClient) {
        MongoDBClient.client = mongoClient;
    }
}
