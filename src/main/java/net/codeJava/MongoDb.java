package net.codeJava;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDb {

    public static MongoCollection<Document> getCollection() {
        String URL = "mongodb://localhost:27017/bd2";
        MongoClient client = MongoClients.create(URL);
        MongoDatabase db = client.getDatabase("bd2");
        MongoCollection<Document> coll = db.getCollection("estadisticas");
        return coll;
    }

    public static void main(String[] args) {
        String URL = "mongodb://localhost:27017/bd2";
        MongoClient client = MongoClients.create(URL);
        MongoDatabase db = client.getDatabase("bd2");
        MongoCollection<Document> coll = db.getCollection("test");
        Document doc = new Document("_id", "3").append("name", "pachito");
        coll.insertOne(doc);
    }
}
