package org.dennis.sample.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author deng.zhang on 2016/5/20.
 */
public class MongoUtil {
    private static MongoClient mongoClient;

    public static void init() {
        if (mongoClient == null) {
            List<ServerAddress> serverAddresses = new ArrayList<ServerAddress>();
            serverAddresses.add(new ServerAddress("192.168.56.100", 27017));
            serverAddresses.add(new ServerAddress("192.168.56.100", 27018));
            serverAddresses.add(new ServerAddress("192.168.56.100", 27019));
            List<MongoCredential> mongoCredentials = new ArrayList<MongoCredential>();
            mongoCredentials.add(MongoCredential.createCredential("dennis", "dennis", "dennisadmin".toCharArray()));
            MongoClientOptions options = MongoClientOptions.builder()
                    //与目标数据库能够简历的最大连接数为50
                    .connectionsPerHost(50)
                    //如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
                    .threadsAllowedToBlockForConnectionMultiplier(50)
                    // 获取链接最大等待时间，这里为2分钟
                    .maxWaitTime(2 * 60 * 1000)
                    // 建立链接的超时时间为1分钟
                    .connectTimeout(60 * 1000)
                    .build();
            //mongoClient = new MongoClient(serverAddresses, mongoCredentials, options);
            mongoClient = new MongoClient(serverAddresses, options);
        }
    }

    /**
     * 获取MongoDB客户端
     *
     * @return MongoDB客户端
     */
    public static MongoClient getClient() {
        return mongoClient;
    }

    /**
     * 根据数据库名获取数据库
     *
     * @param databaseName 数据库名
     * @return Mongo数据库
     */
    public static MongoDatabase getDatabase(String databaseName) {
        //获取数据库，若不存在，会自动创建
        return mongoClient.getDatabase(databaseName);
    }

    /**
     * 根据集合名获取集合
     *
     * @param databaseName   数据库名
     * @param collectionName 集合名
     * @return Collection
     */
    public static MongoCollection<Document> getCollection(String databaseName, String collectionName) {
        //获取集合，若不存在，会自动创建
        return mongoClient.getDatabase(databaseName).getCollection(collectionName);
    }

    /**
     * 根据集合名获取集合
     *
     * @param databaseName   数据库名
     * @param collectionName 集合名
     * @param clazz          集合中存放的数据类型
     * @param <T>            集合中存放的数据类型参数
     * @return
     */
    public static <T> MongoCollection<T> getCollection(String databaseName, String collectionName, Class<T> clazz) {
        //获取集合，若不存在，会自动创建
        return mongoClient.getDatabase(databaseName).getCollection(collectionName, clazz);
    }

    /**
     * 列出指定数据库的所有Collection
     *
     * @param databaseName 数据库名
     * @return Collection列表
     */
    public static Set<String> getCollectionNames(String databaseName) {
        Set<String> collectionNameSet = new HashSet<String>();
        MongoIterable<String> collectionNames = mongoClient.getDatabase(databaseName).listCollectionNames();
        if (collectionNames != null) {
            for (String collectionName : collectionNames) {
                collectionNameSet.add(collectionName);
            }
        }

        return collectionNameSet;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}
