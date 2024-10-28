package com.cloudcomputing.erp.utils;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class EmpleadoMapper {
    private static final Logger logger = Logger.getLogger(EmpleadoMapper.class.getName());  

    public static List<EmpleadoDTO> obtenerEmpleadosDesdeJson() {
        // Instancia de JSON-B
        Jsonb jsonb = JsonbBuilder.create();

        // Leer el archivo JSON y mapearlo a la lista de EmpleadoDTO
        try (InputStream is = EmpleadoMapper.class.getResourceAsStream("/datos/datos.json")) {
            if (is != null) {
                // Leer y mostrar el contenido del archivo para depuración
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                String jsonContent = new String(buffer, StandardCharsets.UTF_8);
                logger.log(Level.INFO, "Contenido del JSON: {0}", jsonContent);

                try {
                    EmpleadoDTO[] empleadosArray = jsonb.fromJson(jsonContent, EmpleadoDTO[].class);
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
                    e.printStackTrace();
                    logger.severe("Error durante la conversión de JSON: " + e.getMessage());
                    return null;
                }
            } else {
                System.err.println("No se encontró el archivo datos.json");
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
