package com.cloudcomputing.erp.controllers.rh;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.services.EmpleadoService;
import com.cloudcomputing.erp.services.NominaService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class GestionNominaEmpleadoController implements Serializable {
    private final EmpleadoService empleadoService;
    private final NominaService nominaService;
    private List<EmpleadoDTO> empleados;

    public GestionNominaEmpleadoController() {
        empleadoService = new EmpleadoService();
        nominaService = new NominaService();
        empleados = empleadoService.listarEmpleados();

        if (empleados == null) {
            empleados = new ArrayList<>();
        } else {
            empleados.forEach(empleado -> System.out.print(empleado.getIdEmpleado() + empleado.getNombre()));
        }
    }
   

    public String generarNomina(String idEmpleado) {
        boolean resultado = nominaService.agregarNomina(idEmpleado);
        if (resultado) {
            empleados = empleadoService.listarEmpleados();
            return "gestionNominaEmpleado.xhtml?faces-redirect=true";
        } else {
            System.err.println("Error al intentar eliminar el empleado con ID: " + idEmpleado);
            return null;
        }
    }

    // Getters y setters
    public List<EmpleadoDTO> getEmpleados() {
        return empleados;
    }
}
