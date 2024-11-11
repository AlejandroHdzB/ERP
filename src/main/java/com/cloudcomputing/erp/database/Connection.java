package com.cloudcomputing.erp.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.Document;

public class Connection {
    private static final Logger logger = Logger.getLogger(Connection.class.getName());

    private static final String CONNECTION_URI = "mongodb+srv://erp:aBozoaUMhNLSZDHn@cluster.yix4x.mongodb.net/?retryWrites=true&w=majority&appName=Cluster";
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    // Método para conectarse a MongoDB Atlas
    public static void connect() {
        try {
            // Crear el cliente de MongoDB
            mongoClient = MongoClients.create(CONNECTION_URI);

            // Obtener la base de datos
            database = mongoClient.getDatabase("test");

            logger.info("Conexión exitosa a MongoDB Atlas");
        } catch (Exception e) {
            logger.log(Level.INFO, "Error al conectarse a MongoDB Atlas: {0}", e.getMessage());
        }
    }

    // Método para obtener una colección
    public static MongoCollection<Document> getCollection(String collectionName) {
        if (database == null) {
            throw new IllegalStateException("No se ha conectado a la base de datos");
        }
        return database.getCollection(collectionName);
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
            logger.info("Conexión cerrada");
        }
    }

}
