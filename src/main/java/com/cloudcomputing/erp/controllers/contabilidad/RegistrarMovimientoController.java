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

    public void agregarMovimientosNomina(String desc, double salarioNeto, double salarioBruto, double deducciones, String id_Fk) {
        //Bancos Codigo 1110 
        //Sueldos y Salarios 5110
        //ISR por Pagar 2110

        transacciones.setFechaMov(new Date());
        transacciones.setTipoMov("Debe");
        transacciones.setFechaAlta(LocalDate.now());
        transacciones.setMonto(salarioBruto);
        transacciones.setCuenta("5110");
        transacciones.setDescripcion(desc);
        transacciones.setIdOperacion(id_Fk);

        boolean resultado = contabilidadService.agregarMovimiento(transacciones);
        if (resultado) {
            boolean resultado2 = agregarISR(deducciones, id_Fk);
            if (resultado2) {
                boolean resultado3 = agregarBanco(salarioNeto, id_Fk);
                if (resultado3) {
                    System.out.println("Los movimientos a los asientos contables han sido insertados correctamente");
                } else {
                    System.out.println("Hubo un problema al Agregar el Asiento Contable de Bancos");

                }
            } else {
                System.out.println("Hubo un problema al Agregar el Asiento Contable de ISR");
            }
        } else {
            System.out.println("Hubo un problema al Agregar el Asiento Contable de Sueldos y Salarios");
        }
    }

    public boolean agregarISR(double deducciones, String id_Fk) {
        transacciones.setFechaMov(new Date());
        transacciones.setTipoMov("Haber");
        transacciones.setFechaAlta(LocalDate.now());
        transacciones.setMonto(deducciones);
        transacciones.setCuenta("2110");
        transacciones.setDescripcion("ISR por Pagar");
        transacciones.setIdOperacion(id_Fk);
        return contabilidadService.agregarMovimiento(transacciones);
    }

    public boolean agregarBanco(double salarioNeto, String id_Fk) {
        transacciones.setFechaMov(new Date());
        transacciones.setTipoMov("Haber");
        transacciones.setFechaAlta(LocalDate.now());
        transacciones.setMonto(salarioNeto);
        transacciones.setCuenta("1110");
        transacciones.setDescripcion("Bancos");
        transacciones.setIdOperacion(id_Fk);
        return contabilidadService.agregarMovimiento(transacciones);
    }

    public class Movimiento {

        private ObjectId id;

        public Movimiento() {
            this.id = new ObjectId(); // Genera un nuevo ObjectId
        }

        public boolean agregarISR(double deducciones, String id_Fk) {
            transacciones.setFechaMov(new Date());
            transacciones.setTipoMov("Haber");
            transacciones.setFechaAlta(LocalDate.now());
            transacciones.setMonto(deducciones);
            transacciones.setCuenta("2100");
            transacciones.setDescripcion("ISR por Pagar");
            transacciones.setIdOperacion(id_Fk);
            return contabilidadService.agregarMovimiento(transacciones);
        }

    }
}
