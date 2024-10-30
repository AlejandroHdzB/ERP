package com.cloudcomputing.erp.utils;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmpleadoMapper {
    private static final Logger logger = Logger.getLogger(EmpleadoMapper.class.getName());  
    private final Gson gson;

    // Constructor de la clase
    public EmpleadoMapper() {
        this.gson = new GsonBuilder().create();
    }

    public List<EmpleadoDTO> obtenerEmpleadosDesdeJson() {
        // Leer el archivo JSON y mapearlo a la lista de EmpleadoDTO
        try (InputStream is = getClass().getResourceAsStream("/datos/datos.json")) {
            if (is != null) {
                // Leer el contenido del archivo para depuración
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                String jsonContent = new String(buffer, StandardCharsets.UTF_8);
                //logger.log(Level.INFO, "Contenido del JSON: {0}", jsonContent);

                try {
                    // Convertir el JSON en un array de EmpleadoDTO usando Gson
                    EmpleadoDTO[] empleadosArray = gson.fromJson(jsonContent, EmpleadoDTO[].class);
                    if (empleadosArray != null && empleadosArray.length > 0) {
                        for (EmpleadoDTO empleado : empleadosArray) {
                            if (empleado != null) {
                                logger.info("Empleado deserializado: " + empleado.getNombre());
                            } else {
                                logger.warning("Empleado deserializado es null");
                            }
                        }
                    }
                    return Arrays.asList(empleadosArray);
                } catch (Exception e) {
                    logger.severe("Error durante la conversión de JSON con Gson: " + e.getMessage());
                    e.printStackTrace();
                    return null;
                }
            } else {
                logger.severe("No se encontró el archivo datos.json");
                return null;
            }
        } catch (IOException e) {
            logger.severe("Error al leer el archivo JSON: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
