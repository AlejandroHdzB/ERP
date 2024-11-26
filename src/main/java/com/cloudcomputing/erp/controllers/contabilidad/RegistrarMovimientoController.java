package com.cloudcomputing.erp.controllers.contabilidad;

import com.cloudcomputing.erp.dto.ContabilidadDTO;
import com.cloudcomputing.erp.services.ContabilidadService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
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


    
    public void crearMovimiento() {

       // Genera un UUID para el idTransaccion antes de agregarlo
    transacciones.setIdTransaccion(UUID.randomUUID().toString());
    /*Prueba Movimeinto Venta
    // Asignar valores estáticos a los demás campos
    transacciones.setFechaMov(new Date());  // Fecha actual (puedes asignar una fecha específica si es necesario)
    transacciones.setTipo_Mov("Haber");     // Ejemplo de tipo de movimiento
    transacciones.setMonto(1000.0);          // Monto estático
    transacciones.setCuenta("1234567890");  // Número de cuenta estático
    transacciones.setDescripcion("Pago de factura");  // Descripción estática*/
    //Prueba Movimiento Compra
    transacciones.setFechaMov(new Date());  // Fecha actual (puedes asignar una fecha específica si es necesario)
    transacciones.setTipoMov("Deber");     // Ejemplo de tipo de movimiento
    transacciones.setMonto(1000.0);          // Monto estático
    transacciones.setCuenta("1234567890");  // Número de cuenta estático
    transacciones.setDescripcion("Pago a Proveedores"); 

    contabilidadService.agregarMovimiento(transacciones);
    
    //return "dashboard.xhtml?faces-redirect=true";
     }
    public class Movimiento {
    private ObjectId id;
    
    public Movimiento() {
        this.id = new ObjectId(); // Genera un nuevo ObjectId
    }

    }
}

    
