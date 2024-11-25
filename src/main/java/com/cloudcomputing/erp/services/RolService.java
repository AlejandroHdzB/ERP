/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.database.Connection;
import com.cloudcomputing.erp.dto.RolDTO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class RolService {
    private final String NAME_COLLECTION = "roles";
    private final Connection connection = new Connection();
    private final Gson gson = new Gson();

    public List<RolDTO> obtenerRoles() {
        List<RolDTO> roles = new ArrayList<>();
        try {
            connection.connect();
            List<Document> documentos = connection.getCollectionData(NAME_COLLECTION);

            if (documentos == null || documentos.isEmpty()) {
                return new ArrayList<>();
            }

            for (Document doc : documentos) {
                try {     
                    if (doc.containsKey("_id")) {
                        ObjectId objId = doc.getObjectId("_id");
                        doc.put("_id", objId.toHexString());
                    }
                    RolDTO empleado = gson.fromJson(doc.toJson(), RolDTO.class);
                    roles.add(empleado);
                } catch (JsonSyntaxException e) {
                    System.err.println("Error al convertir documentos a EmpleadoDTO: " + e.getMessage());
                }
            }
        }catch(Exception e){
            System.err.println("Error al listar los empleados: " + e.getMessage());
        }finally{
            connection.closeConnection();
        } 
        return roles;
    }
    
}
