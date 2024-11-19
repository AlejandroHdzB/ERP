package com.cloudcomputing.erp.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

public class Connection {

    private final String connectionString = "mongodb+srv://erp:aBozoaUMhNLSZDHn@cluster.yix4x.mongodb.net/?retryWrites=true&w=majority&appName=Cluster";
    private MongoClient mongoClient;
    private MongoDatabase database;

    public void connect() {
        try {
            ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();

            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(new ConnectionString(connectionString))
                    .serverApi(serverApi)
                    .build();

            mongoClient = MongoClients.create(settings);
            database = mongoClient.getDatabase("test");

            // Prueba de conexión
            database.runCommand(new Document("ping", 1));
            System.out.println("Conexión exitosa a MongoDB.");
        } catch (MongoException e) {
            System.err.println("Error al conectar a MongoDB: " + e.getMessage());
        }
    }

    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }

    public List<Document> getCollectionData(String collectionName) {
        List<Document> dataList = new ArrayList<>();
        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            collection.find().forEach(dataList::add);
        } catch (Exception e) {
            System.err.println("Error al obtener datos de la colección: " + e.getMessage());
        }
        return dataList;
    }
    
    public Document getDocumentById(String id, String collectionName) {
        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);

            ObjectId objectId = new ObjectId(id);

            Document document = collection.find(new Document("_id", objectId)).first();

            return document;
        } catch (Exception e) {
            System.err.println("Error al obtener el documento: " + e.getMessage());
            return null;
        }
    }

    public void printCollectionData(String collectionName) {
        List<Document> dataList = getCollectionData(collectionName);
        for (Document doc : dataList) {
            System.out.println(doc.toJson());
        }
    }
}
