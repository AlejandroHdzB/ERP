
package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.database.Connection;
import com.cloudcomputing.erp.dto.ContabilidadDTO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import jakarta.mail.internet.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.bson.types.ObjectId;

public class ContabilidadService {

    private final String NAME_COLLECTION = "contabilidad";
    private final Connection connection = new Connection();
    private final Gson gson = new Gson();

    public List<ContabilidadDTO> listarTransacciones() {
        List<ContabilidadDTO> transacciones= new ArrayList<>();
                try {
            connection.connect();
            List<Document> documentos = connection.getCollectionData(NAME_COLLECTION);
            if (documentos == null || documentos.isEmpty()) {
                return new ArrayList<>();
            }
              
            for (Document doc : documentos) {
                try {
                    
                    correcionValoresJSON(doc);
                    
                    ContabilidadDTO transaccion = gson.fromJson(doc.toJson(), ContabilidadDTO.class);
                    
                    transacciones.add(transaccion);
                                System.out.println("Número de transacciones recuperadas: " + transacciones.size());

                } catch (JsonSyntaxException e) {
                    System.err.println("Error al convertir documentos a TransaccioneDTO: " + e.getMessage());
                }
            }
                        System.out.println("Número de transacciones recuperadas: " + transacciones.size());

        }catch(Exception e){
            System.err.println("Error al listar los empleados: " + e.getMessage());
        }finally{
            connection.closeConnection();
        } 

        return transacciones;
    }
    
    
        private void correcionValoresJSON(Document doc) throws java.text.ParseException{
        try {
            if (doc.containsKey("_id")) {
                ObjectId objId = doc.getObjectId("_id");
                doc.put("_id", objId.toHexString());
            }
           if (doc.containsKey("fecha_mov")) {
            // Verificar si "fecha_mov" es una cadena
            Object fechaMovObj = doc.get("fecha_mov");
            if (fechaMovObj instanceof String) {
                // Si es una cadena, intentar convertirla a Date
                String fechaMovStr = (String) fechaMovObj;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  // Formato adecuado para el desplazamiento
                Date fechaMov = sdf.parse(fechaMovStr);
                doc.put("fecha_mov", fechaMov.toInstant().toString());  // Convierte a Instant si es necesario
            } else if (fechaMovObj instanceof Date) {
                // Si ya es un Date, simplemente lo usamos tal cual
                Date fechaMov = (Date) fechaMovObj;
                doc.put("fecha_mov", fechaMov.toInstant().toString());
            }
        }

            if (doc.containsKey("id_compra_venta")) {
                ObjectId objId = doc.getObjectId("id_compra_venta");
                doc.put("id_compra_venta", objId.toHexString());
            }

        } catch (JsonSyntaxException e) {
            System.err.println("Error al convertir documentos a EmpleadoDTO: " + e.getMessage());
        }
    }
}
