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
import org.bson.conversions.Bson;
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
        } catch (MongoException e) {
            System.err.println("Error al conectar a MongoDB: " + e.getMessage());
        }
    }

    public void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
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
    
    public List<Document> getCollectionDataFilter(String collectionName, Bson filter) {
        List<Document> dataList = new ArrayList<>();
        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            collection.find(filter).forEach(dataList::add);
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
    
    public boolean addDocument(String collectionName, Document document) {
        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            collection.insertOne(document);
            System.out.println("Documento agregado correctamente.");
            return true;
        } catch (Exception e) {
            System.err.println("Error al agregar el documento: " + e.getMessage());
            return false;
        }
    }

    public boolean updateDocument(String collectionName, String id, Document updatedFields) {
        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);

            ObjectId objectId = new ObjectId(id);
            Document query = new Document("_id", objectId);
            Document existingDocument = collection.find(query).first();

            if (existingDocument == null) {
                System.out.println("No se encontró el documento con ID: " + id);
                return false;
            }

            for (String key : updatedFields.keySet()) {
                existingDocument.put(key, updatedFields.get(key));
            }
            existingDocument.remove("_id");

            Document update = new Document("$set", existingDocument);

            if (collection.updateOne(query, update).getModifiedCount() > 0) {
                System.out.println("Documento actualizado correctamente.");
                return true;
            } else {
                System.out.println("No se pudo actualizar el documento con ID: " + id);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar el documento: " + e.getMessage());
            return false;
        }
    }


    public boolean deleteDocument(String collectionName, String id) {
        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);

            ObjectId objectId = new ObjectId(id);
            Document query = new Document("_id", objectId);

            if (collection.deleteOne(query).getDeletedCount() > 0) {
                System.out.println("Documento eliminado correctamente.");
                return true;
            } else {
                System.out.println("No se encontró el documento con ID: " + id);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar el documento: " + e.getMessage());
            return false;
        }
    }


    public void printCollectionData(String collectionName) {
        List<Document> dataList = getCollectionData(collectionName);
        for (Document doc : dataList) {
            System.out.println(doc.toJson());
        }
    }
}
