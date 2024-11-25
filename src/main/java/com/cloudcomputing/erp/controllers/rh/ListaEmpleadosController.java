package com.cloudcomputing.erp.controllers.rh;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.services.EmpleadoService;
import com.cloudcomputing.erp.services.NominaService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ListaEmpleadosController implements Serializable {
    private final EmpleadoService empleadoService;
    private List<EmpleadoDTO> empleados;

    public ListaEmpleadosController() {
        empleadoService = new EmpleadoService();
        empleados = empleadoService.listarEmpleados();

        if (empleados == null) {
            empleados = new ArrayList<>();
        } else {
            empleados.forEach(empleado -> System.out.print(empleado.getIdEmpleado() + empleado.getNombre()));
        }
    }
    
    public String eliminarEmpleado(String idEmpleado) {
        boolean resultado = empleadoService.cambiarEstadoEmpleado(idEmpleado, "Inactivo");
        if (resultado) {
            empleados = empleadoService.listarEmpleados();
            return "listaEmpleados.xhtml?faces-redirect=true";
        } else {
            System.err.println("Error al intentar eliminar el empleado con ID: " + idEmpleado);
            return null;
        }
    }

    public void editarEmpleado(String idEmpleado) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("editarEmpleado.xhtml?idEmpleado=" + idEmpleado);
        } catch (IOException e) {
            System.err.println("Error al redirigir a la página de edición: " + e.getMessage());
        }
    }

    // Getters y setters
    public List<EmpleadoDTO> getEmpleados() {
        return empleados;
    }
}
