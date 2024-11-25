package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.database.Connection;
import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.dto.RolDTO;
import com.google.gson.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmpleadoService {
    private final String NAME_COLLECTION = "empleados";
    private final Connection connection = new Connection();
    private final Gson gson;

    public EmpleadoService() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (LocalDate date, Type typeOfSrc, JsonSerializationContext context) -> new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .create();
    }

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
                    correcionDocumento(doc);
                    EmpleadoDTO empleado = gson.fromJson(doc.toJson(), EmpleadoDTO.class);
                    empleados.add(empleado);
                } catch (JsonSyntaxException e) {
                    System.err.println("Error al convertir documentos a EmpleadoDTO: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error al listar los empleados: " + e.getMessage());
        } finally {
            connection.closeConnection();
        }
        return empleados;
    }

    public EmpleadoDTO obtenerEmpleadoPorId(String id) {
        EmpleadoDTO empleadoDTO = null;
        try {
            connection.connect();
            Document docEmp;
            if (ObjectId.isValid(id)) {
                ObjectId objId = new ObjectId(id);
                docEmp = connection.getDocumentById(objId.toHexString(), NAME_COLLECTION);
            } else {
                docEmp = new Document();
            }
            if (docEmp != null) {
                correcionDocumento(docEmp);
                empleadoDTO = gson.fromJson(docEmp.toJson(), EmpleadoDTO.class);
            } else {
                empleadoDTO = new EmpleadoDTO();
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error al recuperar el empleado: " + e.getMessage());
        } finally {
            connection.closeConnection();
        }
        return empleadoDTO;
    }

    public boolean agregarEmpleado(EmpleadoDTO empleadoDTO) {
        try {
            connection.connect();
            String json = gson.toJson(empleadoDTO);

            Document document = Document.parse(json);

            if (empleadoDTO.getRol() != null) {
                document.put("ROL", new ObjectId(empleadoDTO.getRol()));
            }

            boolean resultado = connection.addDocument("empleados", document);

            if (resultado) {
                System.out.println("Empleado agregado correctamente: " + empleadoDTO);
                return true;
            } else {
                System.err.println("Error al agregar el empleado.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al agregar empleado: " + e.getMessage());
            return false;
        } finally {
            connection.closeConnection();
        }
    }
    
    public boolean actualizarEmpleado(EmpleadoDTO empleadoDTO) {
        try {
            connection.connect();
            String json = gson.toJson(empleadoDTO);

            Document document = Document.parse(json);

            if (empleadoDTO.getRol() != null) {
                document.put("ROL", new ObjectId(empleadoDTO.getRol()));
            }

            boolean resultado = connection.updateDocument("empleados", empleadoDTO.getIdEmpleado(), document);

            if (resultado) {
                System.out.println("Empleado actualizado correctamente: " + empleadoDTO);
                return true;
            } else {
                System.err.println("Error al actualizar el empleado.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        } finally {
            connection.closeConnection();
        }
    }
    
    public boolean cambiarEstadoEmpleado(String idEmpleado, String nuevoEstado) {
        try {
            connection.connect();
            Document updatedFields = new Document("estatus", nuevoEstado);
            boolean resultado = connection.updateDocument(NAME_COLLECTION, idEmpleado, updatedFields);

            if (resultado) {
                System.out.println("Estado del empleado actualizado correctamente a: " + nuevoEstado);
                return true;
            } else {
                System.err.println("Error al actualizar el estado del empleado.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al cambiar el estado del empleado: " + e.getMessage());
            return false;
        } finally {
            connection.closeConnection();
        }
    }
    
    private void correcionDocumento(Document doc){
        if (doc.containsKey("_id")) {
            ObjectId objId = doc.getObjectId("_id");
            doc.put("_id", objId.toHexString());
        }

        if (doc.containsKey("ROL")) {
            ObjectId objId = doc.getObjectId("ROL");
            String id = objId.toHexString();
            Document docRol = connection.getDocumentById(id, "roles");
            if (docRol != null) {
                if (docRol.containsKey("_id")) {
                    ObjectId objIdRol = docRol.getObjectId("_id");
                    docRol.put("_id", objIdRol.toHexString());
                }
                RolDTO rol = gson.fromJson(docRol.toJson(), RolDTO.class);
                doc.put("ROL", rol.getRol());
            }
        }
        if (doc.containsKey("fecha_alta")) {
            Object fechaAltaField = doc.get("fecha_alta");
            if (fechaAltaField instanceof Date) {
                Date fechaAlta = (Date) fechaAltaField;
                LocalDate localDateAlta = fechaAlta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                doc.put("fecha_alta", localDateAlta.toString());
            }
        }

        if (doc.containsKey("fecha_nacimiento")) {
            Object fechaNacimientoField = doc.get("fecha_nacimiento");
            if (fechaNacimientoField instanceof Date) {
                Date fechaNacimiento = (Date) fechaNacimientoField;
                LocalDate localDateNacimiento = fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                doc.put("fecha_nacimiento", localDateNacimiento.toString());
            }
        }
    }
}
