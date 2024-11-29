package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.controllers.contabilidad.RegistrarMovimientoController;
import com.cloudcomputing.erp.database.Connection;
import com.google.gson.*;
import org.bson.Document;
import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.dto.NominaDTO;
import com.cloudcomputing.erp.dto.VistaNominaGeneral;
import com.cloudcomputing.erp.utils.GenerarPDF;
import com.cloudcomputing.erp.utils.UploadServlet;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.IOException;
import java.io.InputStream;
import org.bson.types.ObjectId;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NominaService {
    private final String NAME_COLLECTION = "nominas";
    private final Connection connection = new Connection();
    private final EmpleadoService empleadoService = new EmpleadoService();
    private final Gson gson;

    public NominaService() {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (JsonElement json, Type typeOfT, JsonDeserializationContext context) -> LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (LocalDate date, Type typeOfSrc, JsonSerializationContext context) -> new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)))
                .create();
    }

    public boolean agregarNomina(String idEmpleado) {
        System.out.println(idEmpleado);
        try {
            connection.connect();
            EmpleadoDTO empleado = empleadoService.obtenerEmpleadoPorId(idEmpleado);

            if (empleado == null) {
                System.err.println("Empleado no encontrado con ID: " + idEmpleado);
                return false;
            }

            double salarioBruto = empleado.getSalario();
            double deducciones = salarioBruto * 0.16; // Aplicar ISR del 16%
            double salarioNeto = salarioBruto - deducciones;

            NominaDTO nominaDTO = new NominaDTO();
            nominaDTO.setIdEmpleado(idEmpleado);
            nominaDTO.setFechaPago(LocalDate.now());
            nominaDTO.setSalarioBruto(salarioBruto);
            nominaDTO.setDeducciones(deducciones);
            nominaDTO.setSalarioNeto(salarioNeto);

            // Generar PDF de la nómina
            String nombreArchivo = "nomina_" + nominaDTO.getIdEmpleado() + "_" + LocalDate.now() + ".pdf";
            try {
                String ruta = GenerarPDF.generarPdfNomina(nominaDTO, empleado, nombreArchivo);
                System.out.println(ruta);
                UploadServlet subir = new UploadServlet(); 
                InputStream fileContent = Files.newInputStream(Paths.get(ruta)); 
                subir.uploadFile(nombreArchivo, fileContent); 
                System.out.println("PDF de nómina generado: " + ruta);
                nominaDTO.setDocumentoNomina(nombreArchivo);
            } catch (JSchException | SftpException | IOException e) {
                System.err.println("Error al generar el PDF de la nómina: " + e.getMessage());
                return false;
            }

            String json = gson.toJson(nominaDTO);
            Document document = Document.parse(json);
            document.put("id_empleado", new ObjectId(nominaDTO.getIdEmpleado()));
            
            //Registro de la trasaccion en contabilidad
            RegistrarMovimientoController mov = new RegistrarMovimientoController();
            mov.agregarMovimientosNomina("Sueldos y Salarios", salarioNeto);

            boolean resultado = connection.addDocument(NAME_COLLECTION, document);

            if (resultado) {
                System.out.println("Nómina agregada correctamente: " + nominaDTO);
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
    
    public List<NominaDTO> obtenerNomina(){
        List<NominaDTO> listaNominas = new ArrayList<>();
        try {
            connection.connect();
            List<Document> documentos = connection.getCollectionData(NAME_COLLECTION);
            for (Document documento : documentos) {
                ObjectId objNom = documento.getObjectId("_id");
                String idNomina = objNom.toHexString();
                documento.put("_id", idNomina);
                
                ObjectId objEmp = documento.getObjectId("id_empleado");
                String idEmpleado = objEmp.toHexString();
                documento.put("id_empleado", idEmpleado);
                
                NominaDTO nomina = gson.fromJson(documento.toJson(), NominaDTO.class);
                listaNominas.add(nomina);
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error al obtener la nómina general: " + e.getMessage());
        } finally {
            connection.closeConnection();
        }
        return listaNominas;
    }
    
    public List<VistaNominaGeneral> obtenerVistaNominaGeneral() {
        List<VistaNominaGeneral> listaNominas = new ArrayList<>();
        try {
            connection.connect();
            List<NominaDTO> nominas = this.obtenerNomina();
            for (NominaDTO nomina : nominas) {
                EmpleadoDTO empleado = empleadoService.obtenerEmpleadoPorId(nomina.getIdEmpleado());
                if (empleado != null) {
                    VistaNominaGeneral nominaGeneral = new VistaNominaGeneral();
                    nominaGeneral.setIdNomina(nomina.getIdNomina());
                    nominaGeneral.setIdEmpleado(nomina.getIdEmpleado());
                    nominaGeneral.setNombreEmpleado(empleado.getNombre());
                    nominaGeneral.setApellidoPaterno(empleado.getApellidoPaterno());
                    nominaGeneral.setApellidoMaterno(empleado.getApellidoMaterno());
                    nominaGeneral.setSalarioBruto(nomina.getSalarioBruto());
                    nominaGeneral.setSalarioNeto(nomina.getSalarioNeto());
                    nominaGeneral.setFechaPago(nomina.getFechaPago());
                    nominaGeneral.setDeducciones(nomina.getDeducciones());
                    nominaGeneral.setDocumentoNomina(nomina.getDocumentoNomina());
                    listaNominas.add(nominaGeneral);
                }
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error al obtener la nómina general: " + e.getMessage());
        } finally {
            connection.closeConnection();
        }
        return listaNominas;
    }
    
    public List<NominaDTO> obtenerNominasPorEmpleado(String idEmpleado) {
        List<NominaDTO> listaNominas = new ArrayList<>();
        try {
            connection.connect();
            List<Document> documentos = connection.getCollectionData(NAME_COLLECTION);
            for (Document documento : documentos) {
                ObjectId objEmp = documento.getObjectId("id_empleado");
                if (objEmp.toHexString().equals(idEmpleado)) {
                    documento.put("_id", documento.getObjectId("_id").toHexString());
                    documento.put("id_empleado", objEmp.toHexString());
                    NominaDTO nomina = gson.fromJson(documento.toJson(), NominaDTO.class);
                    listaNominas.add(nomina);
                }
            }
        } catch (JsonSyntaxException e) {
            System.err.println("Error al obtener nóminas del empleado con ID " + idEmpleado + ": " + e.getMessage());
        } finally {
            connection.closeConnection();
        }
        return listaNominas;
    }

}
