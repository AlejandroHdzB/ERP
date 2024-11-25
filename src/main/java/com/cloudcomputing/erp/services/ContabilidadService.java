package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.database.Connection;
import com.cloudcomputing.erp.dto.ContabilidadDTO;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mongodb.client.model.Filters;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class ContabilidadService {

    private final String NAME_COLLECTION = "contabilidad";
    private final Connection connection = new Connection();
    private final Gson gson = new Gson();

 public List<ContabilidadDTO> listarTransaccionesPorFecha() {
    List<ContabilidadDTO> transacciones = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"); // Definir el formato de fecha
    Date fechaInicio = null;
    Date fechaFin = null;

    try {
        connection.connect();

        Date fechaHoy = new Date();  

        String fechaInicioStr = sdf.format(fechaHoy).split("T")[0] + "T00:00:00.000+00:00";
        String fechaFinStr = sdf.format(fechaHoy).split("T")[0] + "T23:59:59.999+00:00";

        fechaInicio = sdf.parse(fechaInicioStr);
        fechaFin = sdf.parse(fechaFinStr);

        Bson filter = Filters.and(
                Filters.gte("fecha_mov", fechaInicio),
                Filters.lte("fecha_mov", fechaFin)
        );

        List<Document> documentos = connection.getCollectionDataFilter(NAME_COLLECTION, filter);

        // Verificar si se encontraron documentos
        if (documentos == null || documentos.isEmpty()) {
            System.out.println("No se encontraron transacciones para la fecha: " + fechaHoy);
            return transacciones; // Retorna lista vacía si no hay datos
        }

        // Convertir los documentos a DTO
        for (Document doc : documentos) {
            correcionValoresJSON(doc);
            ContabilidadDTO transaccion = gson.fromJson(doc.toJson(), ContabilidadDTO.class);
            transacciones.add(transaccion);
        }
    } catch (Exception e) {
        System.err.println("Error al listar transacciones por fecha: " + e.getMessage());
    } finally {
        connection.closeConnection();
    }
    return transacciones;
}



    public void agregarMovimiento(ContabilidadDTO contabilidadDTO) {
        try {
            connection.connect();
            String json = gson.toJson(contabilidadDTO);

            Document document = Document.parse(json);

            boolean resultado = connection.addDocument("contabilidad", document);

            if (resultado) {
                System.out.println("Empleado agregado correctamente: " + contabilidadDTO);
            } else {
                System.err.println("Error al agregar el empleado.");
            }
        } catch (Exception e) {
            System.err.println("Error al agregar empleado: " + e.getMessage());
        } finally {
            connection.closeConnection();
        }
    }

    private void correcionValoresJSON(Document doc) throws java.text.ParseException {
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
                    sdf.setTimeZone(TimeZone.getTimeZone("America/Mexico_City"));  // Establecer zona horaria CS
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
