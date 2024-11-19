/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudcomputing.erp.utils;

import com.cloudcomputing.erp.dto.ContabilidadDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ContabilidadMapper {
     private static final Logger logger = Logger.getLogger(ContabilidadMapper.class.getName());  

    public static List<ContabilidadDTO> obtenerTransDesdeJson() {
        // Instancia de Gson
        Gson gson = new GsonBuilder().create();

        // Leer el archivo JSON y mapearlo a la lista de EmpleadoDTO
        try (InputStream is = ContabilidadMapper.class.getResourceAsStream("/datos/datos_1.json")) {
            if (is != null) {
                // Leer el contenido del archivo para depuración
                byte[] buffer = new byte[is.available()];
                is.read(buffer);
                String jsonContent = new String(buffer, StandardCharsets.UTF_8);
                logger.log(Level.INFO, "Contenido del JSON: {0}", jsonContent);

                try {
                    // Convertir el JSON en un array de EmpleadoDTO usando Gson
                    ContabilidadDTO[] transArray = gson.fromJson(jsonContent, ContabilidadDTO[].class);
                    if (transArray != null && transArray.length > 0) {
                        for (ContabilidadDTO trans : transArray) {
                            if (trans != null) {
                                logger.info("Empleado deserializado: " + trans.getIdMovimiento());
                            } else {
                                logger.warning("Empleado deserializado es null");
                            }
                        }
                    }
                    return Arrays.asList(transArray);
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
