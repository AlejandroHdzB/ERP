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
public class listaTransaccionesController implements Serializable {
    
    private final ContabilidadService contabilidadService;
    private List<ContabilidadDTO> transacciones;
    
    public listaTransaccionesController() {
    contabilidadService = new ContabilidadService();
    transacciones = contabilidadService.listarTransacciones();

    if (transacciones == null || transacciones.isEmpty()) {
        System.out.println("No se encontraron transacciones.");
        transacciones = new ArrayList<>();
    } else {

        transacciones.forEach(trans -> System.out.print(trans.getIdTransaccion() + " " + trans.getTipo_Mov()));
    }
}

    
        public List<ContabilidadDTO> getTransacciones() {
        return transacciones;
    }

    
    
}
