package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.database.Connection;
import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.dto.RolDTO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class EmpleadoService {
    private final String NAME_COLLECTION = "empleados";
    private final Connection connection = new Connection();
    private final Gson gson = new Gson();
    
    public List<EmpleadoDTO> listarEmpleados() {
        List<EmpleadoDTO> empleados = new ArrayList<>();
        try {
            connection.connect();
            List<Document> documentos = connection.getCollectionData(NAME_COLLECTION);

            if (documentos == null || documentos.isEmpty()) {
                return new ArrayList<>();
            }

            for (Document doc : documentos) {
                try {
                    correcionValoresJSON(doc);
                    
                    EmpleadoDTO empleado = gson.fromJson(doc.toJson(), EmpleadoDTO.class);
                    empleados.add(empleado);
                } catch (JsonSyntaxException e) {
                    System.err.println("Error al convertir documentos a EmpleadoDTO: " + e.getMessage());
                }
            }
        }catch(Exception e){
            System.err.println("Error al listar los empleados: " + e.getMessage());
        }finally{
            connection.closeConnection();
        } 
        return empleados;
    }
    
    public EmpleadoDTO obtenerEmpleadoPorId(String id){
        EmpleadoDTO empleadoDTO = null;
        try{
            connection.connect();
            Document docEmp;
            if (ObjectId.isValid(id)) {
                ObjectId objId = new ObjectId(id);
                System.out.println(objId.toHexString());
                docEmp = connection.getDocumentById(objId.toHexString(), NAME_COLLECTION);
            }else{
                docEmp = new Document();
            }
            if (docEmp != null) {
                correcionValoresJSON(docEmp);
                empleadoDTO = gson.fromJson(docEmp.toJson(), EmpleadoDTO.class);
            }else{
                empleadoDTO = new EmpleadoDTO();
            }  
        }catch(JsonSyntaxException e){
            System.err.println("Error al recuperar el empleado: " + e.getMessage());
        }finally{
            connection.closeConnection();
        }
        return empleadoDTO;
    }
    
    private void correcionValoresJSON(Document doc){
        try {
            if (doc.containsKey("_id")) {
                ObjectId objId = doc.getObjectId("_id");
                doc.put("_id", objId.toHexString());
            }
            if (doc.containsKey("fecha_alta")) {
                Date fechaAlta = doc.getDate("fecha_alta");
                doc.put("fecha_alta", fechaAlta.toInstant().toString());
            }

            if (doc.containsKey("fecha_nacimiento")) {
                Date fechaNacimiento = doc.getDate("fecha_nacimiento");
                doc.put("fecha_nacimiento", fechaNacimiento.toInstant().toString());
            }

            if (doc.containsKey("ROL")) {
                ObjectId objId = doc.getObjectId("ROL");
                String id = objId.toHexString();
                Document docRol = connection.getDocumentById(id, "roles");
                docRol.put("_id", id);
                RolDTO rol = gson.fromJson(docRol.toJson(), RolDTO.class);
                doc.put("ROL", rol.getRol());
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error al convertir documentos a EmpleadoDTO: " + e.getMessage());
        }
    }
    
}
