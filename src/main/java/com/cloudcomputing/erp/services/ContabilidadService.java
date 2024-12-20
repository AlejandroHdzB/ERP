package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.database.Connection;
import com.cloudcomputing.erp.dto.ContabilidadDTO;
import com.cloudcomputing.erp.utils.GenerarCSV;
import com.cloudcomputing.erp.utils.GenerarPDF;
import com.cloudcomputing.erp.utils.UploadServlet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import org.bson.Document;
import org.bson.types.ObjectId;

public class ContabilidadService {

    private final String NAME_COLLECTION = "contabilidad";
    private final Connection connection = new Connection();
    private final Gson gson;

    public ContabilidadService() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (LocalDate date, Type typeOfSrc, JsonSerializationContext context) -> new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .create();
    }

    public List<ContabilidadDTO> listarTransacciones() {
        List<ContabilidadDTO> transacciones = new ArrayList<>();
        try {
            connection.connect();

            List<Document> documentos = connection.getCollectionData(NAME_COLLECTION);

            // Verificar si se encontraron documentos
            if (documentos == null || documentos.isEmpty()) {
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

    public List<ContabilidadDTO> listarTransaccionesPorFecha(String fecha) {
        List<ContabilidadDTO> transacciones = new ArrayList<>();
        try {
            connection.connect();

            // Crear filtro para buscar por fecha_alta
            Document filtro = new Document("fecha_alta", fecha);

            List<Document> documentos = connection.getCollectionDataFilter(NAME_COLLECTION, filtro);

            // Verificar si se encontraron documentos
            if (documentos == null || documentos.isEmpty()) {
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

    public List<String> listarTransaccionesPorDistinct() {
        List<String> fechasUnicas = new ArrayList<>();
        String fieldName = "fecha_alta"; // Campo de fecha para obtener los valores distintos

        try {
            connection.connect();
            // Obtener las fechas distintas de la colección
            fechasUnicas = connection.getCollectionDataDistinct(NAME_COLLECTION, fieldName);

            // Verificar si no se encontraron fechas
            if (fechasUnicas == null || fechasUnicas.isEmpty()) {
                return fechasUnicas; // Retorna lista vacía si no hay fechas
            }
        } catch (Exception e) {
            System.err.println("Error al listar transacciones por fecha: " + e.getMessage());
        } finally {
            connection.closeConnection();
        }

        return fechasUnicas;
    }

    public boolean agregarMovimiento(ContabilidadDTO contabilidadDTO) {
        try {
            connection.connect();

            // Convertir a JSON y luego a Document
            String json = gson.toJson(contabilidadDTO);
            Document document = Document.parse(json);

            // Reemplazar el campo fechaMov con el tipo correcto
            if (contabilidadDTO.getFechaMov() != null) {
                // Ajustar la fecha a la zona horaria de México
                ZonedDateTime zonedDateTime = contabilidadDTO.getFechaMov()
                        .toInstant()
                        .atZone(ZoneId.of("America/Mexico_City"));

                // Formatear la fecha en formato ISO sin zona horaria
                String fechaFormateada = zonedDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

                // Guardar la fecha como String (sin zona horaria)
                document.put("fecha_mov", fechaFormateada);  // Ahora guardamos la fecha como String en el documento

                System.out.println("Fecha ajustada y formateada como String sin zona horaria: " + fechaFormateada);
            }
            if (contabilidadDTO.getIdOperacion() != null) {
                document.put("id_operacion", new ObjectId(contabilidadDTO.getIdOperacion()));
            }

            // Guardar el documento en la colección
            boolean resultado = connection.addDocument("contabilidad", document);

            if (resultado) {
                System.out.println("Movimiento agregado correctamente: " + contabilidadDTO);
                return true;
            } else {
                System.err.println("Error al agregar el movimiento.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al agregar movimiento: " + e.getMessage());
            return false;
        } finally {
            connection.closeConnection();
        }
    }

    public boolean generarListadoPDF(String fecha) {
        try {
            connection.connect();
            List<ContabilidadDTO> movDia = listarTransaccionesPorFecha(fecha);

            if (movDia == null) {
                System.err.println("Movimeintos no encontrados con fecha: ");
                return false;
            }

            // Generar PDF de la nómina
            String nombreArchivo = "BosquejoLibroDiario_" + fecha + ".pdf";
            try {
                String ruta = GenerarPDF.generarPdfLibro(movDia, fecha, nombreArchivo);
                UploadServlet subir = new UploadServlet();
                InputStream fileContent = Files.newInputStream(Paths.get(ruta));
                subir.uploadFile(nombreArchivo, fileContent);
            } catch (Exception e) {
                System.err.println("Error al generar el PDF de la nómina: " + e.getMessage());
                return false;
            }

            boolean resultado = true;
            if (resultado) {
                System.out.println("Nómina agregada correctamente: ");
                return true;
            } else {
                System.err.println("Error al agregar la nómina.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al agregar nómina: " + e.getMessage());
            return false;
        } finally {
            connection.closeConnection();
        }

    }

    public boolean generarListadoCSV(String fecha) {
        try {
            connection.connect();
            List<ContabilidadDTO> movDia = listarTransaccionesPorFecha(fecha);

            if (movDia == null) {
                System.err.println("Movimeintos no encontrados con fecha: ");
                return false;
            }

            // Generar CSV
            String nombreArchivoCSV = "BosquejoLibroDiario_" + fecha + ".csv";
            try {
                String ruta = GenerarCSV.generarCsvLibro(movDia, fecha, nombreArchivoCSV);
                //System.out.println(ruta);
                UploadServlet subir = new UploadServlet();
                InputStream fileContent = Files.newInputStream(Paths.get(ruta));
                subir.uploadFile(nombreArchivoCSV, fileContent);
                System.out.println("Archivo CSV subido exitosamente.");
            } catch (Exception e) {
                System.err.println("Error al generar el CSV: " + e.getMessage());
                return false;
            }

            boolean resultado = true;
            if (resultado) {
                System.out.println("Nómina agregada correctamente: ");
                return true;
            } else {
                System.err.println("Error al agregar la nómina.");
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error al agregar nómina: " + e.getMessage());
            return false;
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
            if (doc.containsKey("fecha_alta")) {
                Object fechaAltaField = doc.get("fecha_alta");
                if (fechaAltaField instanceof Date) {
                    Date fechaAlta = (Date) fechaAltaField;
                    LocalDate localDateAlta = fechaAlta.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    doc.put("fecha_alta", localDateAlta.toString());
                }
            }

            if (doc.containsKey("id_operacion")) {
                // Obtener el ObjectId y convertirlo a String
                ObjectId objId = doc.getObjectId("id_operacion");
                String id = objId.toHexString();
                // Aquí ya no es necesario hacer una consulta, solo agregamos el id como string
                doc.put("id_operacion", id); // o el campo que quieras actualizar con el id en formato String
            }

        } catch (JsonSyntaxException e) {
            System.err.println("Error al convertir documentos a EmpleadoDTO: " + e.getMessage());
        }
    }
}
