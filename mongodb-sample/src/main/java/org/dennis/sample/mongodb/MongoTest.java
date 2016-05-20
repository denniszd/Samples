package org.dennis.sample.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.UpdateOneModel;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.*;

/**
 * @author deng.zhang on 2016/5/20.
 */
public class MongoTest {
    @Before
    public void before() {
        MongoUtil.init();
    }

    @Test
    public void testQueryCount() {
        MongoCollection collection = MongoUtil.getCollection("dennis", "test");
        System.out.println(collection.count());
    }

    @Test
    public void testInsert() {
        MongoCollection<Document> collection = MongoUtil.getCollection("dennis", "test");
        System.out.println("插入数据前：");
        for (Document document : collection.find()) {
            System.out.println(document.toJson());
        }
        Document insertedDocument = new Document();
        Random random = new Random();
        int randomInt = random.nextInt(50);
        insertedDocument.append("name", "dennis" + randomInt);
        insertedDocument.append("gender", "male");
        insertedDocument.append("age", 25);
        System.out.println("插入的数据： " + insertedDocument.toJson());
        collection.insertOne(insertedDocument);
        System.out.println("插入数据后：");
        for (Document document : collection.find()) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testInsertMany() {
        MongoCollection<Document> collection = MongoUtil.getCollection("dennis", "test");
        System.out.println("插入数据前：");
        for (Document document : collection.find()) {
            System.out.println(document.toJson());
        }
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < 20; i++) {
            Document document = new Document();
            Random random = new Random();
            int randomInt = random.nextInt(50);
            document.append("name", "dennis" + randomInt);
            document.append("gender", "male");
            document.append("age", 18);

            documents.add(document);
        }
        collection.insertMany(documents);
        System.out.println("插入数据后：");
        for (Document document : collection.find()) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testDelete() {
        MongoCollection<Document> collection = MongoUtil.getCollection("dennis", "test");
        long count = collection.count();
        System.out.println("删除前总记录数：" + count);
        Random random = new Random();
        int randomInt = random.nextInt(50);
        Document document = collection.find(eq("name", "dennis" + randomInt)).first();
        if (document != null) {
            collection.deleteOne(eq("name", "dennis" + randomInt));
            System.out.println("删除后总记录数：" + collection.count());
        }
    }

    @Test
    public void testUpdate() {
        MongoCollection<Document> collection = MongoUtil.getCollection("dennis", "test");
        Document document = collection.find(eq("name", "dennis1")).first();
        System.out.println("修改前：" + document.toJson());
        collection.updateOne(eq("name", "dennis1"), new Document("$set", new Document("status", "updated")));
        System.out.println("修改后： " + collection.find(eq("name", "dennis1")).first().toJson());
    }

    @Test
    public void testQueryFirst() {
        MongoCollection<Document> collection = MongoUtil.getCollection("dennis", "test");
        Document document = collection.find().first();
        System.out.println(document.toJson());
    }

    @Test
    public void testQueryAll() {
        MongoCollection<Document> collection = MongoUtil.getCollection("dennis", "test");
        for (Document document : collection.find()) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testQueryByEqCondition() {
        MongoCollection<Document> collection = MongoUtil.getCollection("dennis", "test");
        Document condition = new Document();
        condition.append("name", "dennis29");
//        for (Document document : collection.find(condition)) {
//            System.out.println(document.toJson());
//        }
        for (Document document : collection.find(eq("name", "dennis29"))) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testQueryByGtCondition() {
        MongoCollection<Document> collection = MongoUtil.getCollection("dennis", "test");
        for (Document document : collection.find(gt("age", 20))) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testQueryByAndCondition() {
        MongoCollection<Document> collection = MongoUtil.getCollection("dennis", "test");
        for (Document document : collection.find(and(eq("name", "dennis8"), eq("age", 18)))) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testQueryByLtCondition() {
        MongoCollection<Document> collection = MongoUtil.getCollection("dennis", "test");
        for (Document document : collection.find(lt("age", 21))) {
            System.out.println(document.toJson());
        }
    }

    @Test
    public void testBulkWrite() {
        MongoCollection<Document> collection = MongoUtil.getCollection("dennis", "test");
        collection.bulkWrite(Arrays.asList(
                new InsertOneModel<Document>(new Document("name", "deng1")),
                new InsertOneModel<Document>(new Document("name", "deng2")),
                new DeleteOneModel<Document>(new Document("name", "dennis8")),
                new UpdateOneModel<Document>(eq("name", "dennis1"), new Document("$set", new Document("updateStatus", "bulk")))
        ));
    }

    @After
    public void after() {
        MongoUtil.close();
    }
}
