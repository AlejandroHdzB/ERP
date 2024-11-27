
package com.cloudcomputing.erp.controllers.contabilidad;

import com.cloudcomputing.erp.dto.ContabilidadDTO;
import com.cloudcomputing.erp.services.ContabilidadService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Named
@RequestScoped
public class listaTransaccionesController implements Serializable {
      private final ContabilidadService contabilidadService;
    private List<ContabilidadDTO> transacciones;

    public listaTransaccionesController() {
        contabilidadService = new ContabilidadService();
        
        // Obtener las transacciones del día actual
        transacciones = contabilidadService.listarTransacciones();

        if (transacciones == null || transacciones.isEmpty()) {
            System.out.println("No se encontraron transacciones para hoy.");
            transacciones = new ArrayList<>();
        } else {
            transacciones.forEach(trans -> System.out.println(trans.getIdTransaccion() + " " + trans.getTipoMov()));
        }
    }

    public List<ContabilidadDTO> getTransacciones() {
        return transacciones;
    }
}
