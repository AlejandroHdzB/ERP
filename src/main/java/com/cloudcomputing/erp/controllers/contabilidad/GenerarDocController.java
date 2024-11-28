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
    private List<ContabilidadDTO> movimientos;

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
        boolean resultado = contabilidadService.generarListadoPDF(fecha);
        if (resultado) {
            //movimientos = contabilidadService.listarEmpleados();
            System.out.println("Creo que Funciono ");
        } else {
            System.err.println("Error al intentar generar el PDF de la fecha"+fecha);
         }
            
    }
    
     public void descargarCSV(String fecha){
         boolean resultado = contabilidadService.generarListadoCSV(fecha);
        if (resultado) {
            //movimientos = contabilidadService.listarEmpleados();
            System.out.println("Creo que Funciono CSV");
        } else {
            System.err.println("Error al intentar generar el PDF de la fecha"+fecha);
         }
        
    }

    public List<String> getTransacciones() {
        return transacciones;
    }

}
