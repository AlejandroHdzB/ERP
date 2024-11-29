package com.cloudcomputing.erp.controllers.contabilidad;

import com.cloudcomputing.erp.dto.ContabilidadDTO;
import com.cloudcomputing.erp.services.ContabilidadService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import org.bson.types.ObjectId;


@Named
@RequestScoped
public class RegistrarMovimientoController {
        private final ContabilidadService contabilidadService;
        private ContabilidadDTO transacciones;
        private Movimiento movimiento;


    public RegistrarMovimientoController() {
        contabilidadService = new ContabilidadService();
        transacciones = new ContabilidadDTO();

    }

    public ContabilidadDTO getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(ContabilidadDTO transacciones) {
        this.transacciones = transacciones;
    }


    
    public void agregarMovimientosNomina(String desc,double monto) {
  //Bancos Codigo 1110 
  //Sueldos y Salarios 5110
  
    transacciones.setFechaMov(new Date());  
    transacciones.setTipoMov("Debe");     
    transacciones.setFechaAlta(LocalDate.now());   
    transacciones.setMonto(monto);          
    transacciones.setCuenta("5110");  
    transacciones.setDescripcion(desc); 
    
    boolean resultado=contabilidadService.agregarMovimiento(transacciones);   
    if(resultado)
    {
       System.out.println("Se ha agregado la nomina sin ningun inconveniente");

    }else{
        System.out.println("Hubo un problema al Agregar el Asiento Contable");
    }
     }
    
    
    
    public class Movimiento {
    private ObjectId id;
    
    public Movimiento() {
        this.id = new ObjectId(); // Genera un nuevo ObjectId
    }

    }
}

    
