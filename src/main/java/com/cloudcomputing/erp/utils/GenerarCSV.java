package com.cloudcomputing.erp.utils;

import com.cloudcomputing.erp.dto.ContabilidadDTO;
import com.itextpdf.text.DocumentException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GenerarCSV {

    public static String generarCsvLibro(List<ContabilidadDTO> movDia, String fecha, String nombreArchivoCSV) throws Exception {
        try {
            // Definir la ruta del archivo
            Path ruta = Paths.get("C:/Users/jessey/JesusPdf", nombreArchivoCSV);

            // Crear la carpeta si no existe
            if (!Files.exists(ruta.getParent())) {
                Files.createDirectories(ruta.getParent());
            }

            // Crear el archivo CSV
            try (PrintWriter writer = new PrintWriter(new FileWriter(ruta.toString()))) {
                // Escribir encabezados
                writer.println("Fecha,Descripción,Cuenta,Haber,Debe");

                // Escribir cada registro
                for (ContabilidadDTO movimiento : movDia) {
                    String haber = "";
                    String debe = "";

                    // Diferenciar montos según tipoMov
                    if ("Haber".equalsIgnoreCase(movimiento.getTipoMov())) {
                        haber = String.format("%.2f", movimiento.getMonto());
                    } else if ("Debe".equalsIgnoreCase(movimiento.getTipoMov())) {
                        debe = String.format("%.2f", movimiento.getMonto());
                    }

                    // Escribir fila en el CSV
                    writer.printf("%s,%s,%s,%s,%s%n",
                            movimiento.getFechaAlta(),
                            movimiento.getDescripcion(),
                            movimiento.getCuenta(),
                            haber,
                            debe);
                }
            }

            // Devolver la ruta del archivo generado
            System.out.println("CSV generado correctamente: " + ruta.toString());
            return ruta.toString();

        } catch (IOException e) {
            throw new Exception("Error al escribir el archivo CSV: " + e.getMessage());
        }
    }

}
