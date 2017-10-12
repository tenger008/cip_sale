package com.cip.sale.util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bson.Document;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.WriteModel;

import static com.mongodb.client.model.Filters.gt; 
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.lt;


public class MongoDBJDBC {

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        try {
            /** 直接登录 */
            // To directly connect to a single MongoDB server.
            // (this will not auto-discover the primary even if it's a member of a replica set)
            /*
            MongoClient mongoClient = new MongoClient("192.168.253.10", 27017);
            MongoDatabase mongoDatabase = mongoClient.getDatabase("dsp");
            System.out.println("Connect to database successfully!");
             */

            /** 使用URI连接信息连接 */
            /*
            MongoClientURI connectionString = new MongoClientURI("mongodb://192.168.253.10:27017,192.168.253.20:27018,192.168.253.30:27019");
            MongoClient mongoClient = new MongoClient(connectionString);
             */

            /** Replica Set 认证登录 */
            // to connect to a replica set, with auto-discovery of the primary, supply a seed list of members
            // 数据库URI（两个参数分别为：服务器地址、端口）
            ServerAddress serverAddress = new ServerAddress("127.0.0.1", 27017);
            List<ServerAddress> addressList = new ArrayList<ServerAddress>();
            addressList.add(serverAddress);
            // 认证信息（三个参数分别为：用户名、数据库名称、密码） 
            MongoCredential credential = MongoCredential.createScramSha1Credential("admin", "admin", "admin".toCharArray());
            List<MongoCredential> credentialList = new ArrayList<MongoCredential>();
            credentialList.add(credential);
            // 根据URI和认证信息获取数据库连接
            MongoClient mongoClient = new MongoClient(addressList, credentialList);

            /** 切换数据库 */
            MongoDatabase mongoDatabase = mongoClient.getDatabase("cip");
            /** 切换到需要操作的集合 */
            MongoCollection collection = mongoDatabase.getCollection("book");

            /** 插入文档 */  
            // 创建文档 org.bson.Document对象，参数为K:V格式
            // 创建文档集合List<Document> 
            // 将文档集合插入数据库集合中collection.insertMany(List<Document>)，插入单个文档可以用collection.insertOne(Document)
           /*
            List<Document> docList = new ArrayList<Document>();
            for (int idx = 0; idx < 10; ++idx) {
                Document doc = new Document("title", "MongoDB" + idx).
                        append("desc", "数据库" + idx).
                        append("likes", 100 + idx * 10).
                        append("by", "dsp" + idx);
                docList.add(doc);
            }
            collection.insertMany(docList);*/

            /** 更新文档 */
            //将likes为90到100之间的数变成30     数值比较$gt（大于）、$lt（小于）、$gte（大于等于）、$lte（小于等于）、
           /* UpdateResult updateResult = collection.updateMany(and(gt("likes", 90), lt("likes", 110)), new Document("$set", new Document("likes", 30)));
            updateResult.getMatchedCount();
            updateResult.getUpsertedId();
            updateResult.getModifiedCount();*/
            

            /** 删除文档 */
            
           /* DeleteResult deleteResult = collection.deleteMany(Filters.eq("likes", 30));
            System.out.println("本次删除 " + deleteResult.getDeletedCount() + " 条记录！");*/
            

            /** 检索文档 */
           /* FindIterable<Document> findIterable = collection.find();
            MongoCursor<Document> mongoCursor = findIterable.iterator();
            try {
                while (mongoCursor.hasNext()) {
                    System.out.println(mongoCursor.next().toJson());
                }
            } finally {
                mongoCursor.close();
            }
            */
            
            // 查询过滤器
            
            Document myDoc = (Document) collection.find(eq("likes", 120)).first();
            System.out.println(myDoc.toJson());
            
        
            /** 使用范围查询获取一个较大的子集 *///通过查询语句一次性获取多个数据
            Block<Document> printBlock = new Block<Document>() {
                @Override
                public void apply(Document doc) {
                    System.out.println(doc.toJson());
                }
            };
            // 过滤 likes > 10
            // collection.find(gt("likes", 10)).forEach(printBlock);
            // 过滤 10 <= likes <= 100
            // collection.find(and(Filters.gte("likes", 10), Filters.lte("likes", 100))).forEach(printBlock);
            
            /** 排序 */
             //collection.find(Filters.exists("likes")).sort(Sorts.descending("likes")).limit(2).forEach(printBlock);
            
            /** 查找第一个 */
             /*Document myDoc = (Document) collection.find().projection(Projections.excludeId()).first();
             System.out.println(myDoc.toJson());*/
          //Document myDoc = (Document) collection.find().first();
           //System.out.println(myDoc.toJson());
            /** ordered bulk writes */
            List<WriteModel<Document>> writes = new ArrayList<WriteModel<Document>>();
           /* writes.add(new InsertOneModel<Document>(new Document("_id", 13)));
            writes.add(new InsertOneModel<Document>(new Document("_id", 14)));
            writes.add(new InsertOneModel<Document>(new Document("_id", 15)));*/
            writes.add(new UpdateOneModel<Document>(new Document("_id", 10), new Document("$set", new Document("x", 101010))));
            writes.add(new DeleteOneModel<Document>(new Document("_id", 11)));
            writes.add(new ReplaceOneModel<Document>(new Document("_id", 12), new Document("_id", 12).append("x", 121212)));
            // bulkWrite默认BulkWriteOptions
            // collection.bulkWrite(writes);
             //collection.find().forEach(printBlock);
            // collection.bulkWrite(writes, new BulkWriteOptions().ordered(false));
            // collection.find().forEach(printBlock);
            
            /** drop集合 */
            // collection.drop();
            
            /** drop数据库 */
            // mongoDatabase.drop();
        } catch (Exception e) {  
            System.err.println(e.getClass().getName() + " : " + e.getMessage());  
        } finally {
            // 防止意外，关闭数据库连接
        	//mongoClient.close();
        }
    }

}