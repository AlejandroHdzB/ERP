/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudcomputing.erp.controllers.contabilidad;

import com.cloudcomputing.erp.dto.ContabilidadDTO;
import com.cloudcomputing.erp.services.ContabilidadService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class GenerarDocController implements Serializable {

    private final ContabilidadService contabilidadService;
    private List<String> transacciones;

    public GenerarDocController() {
        contabilidadService = new ContabilidadService();

        // Obtener las fechas únicas (fechas de alta de las transacciones)
        transacciones = contabilidadService.listarTransaccionesPorDistinct();
        // Verificar si no hay fechas
        if (transacciones == null || transacciones.isEmpty()) {
            System.out.println("No se encontraron transacciones para hoy.");
            transacciones = new ArrayList<>();
        } else {
            //Imprimir las fechas (u otros datos si es necesario)
            transacciones.forEach(fecha -> System.out.println("Fecha: " + fecha));
        }
    }
    
    public void descargarPDF(String fecha){
        System.out.println("Descargando PDF... "+fecha);
        
    }
    
     public void descargarCSV(String fecha){
        System.out.println("Descargando CSV... "+fecha);
        
    }

    public List<String> getTransacciones() {
        return transacciones;
    }

}
