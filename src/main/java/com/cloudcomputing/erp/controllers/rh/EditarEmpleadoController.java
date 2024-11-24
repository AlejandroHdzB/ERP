package com.cloudcomputing.erp.controllers.rh;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.services.EmpleadoService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class EditarEmpleadoController {

    private final EmpleadoService empleadoService;
    private final String idEmpleado;
    private EmpleadoDTO empleado;

    public EditarEmpleadoController() {
        empleadoService = new EmpleadoService();
        idEmpleado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idEmpleado");
        System.out.println("ID de empleado: " + idEmpleado);
        
        empleado = empleadoService.obtenerEmpleadoPorId(idEmpleado);
    }

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    // Método para guardar los cambios
    public void guardarCambios() {
        // Lógica para actualizar el empleado
        System.out.println("Guardando cambios para: " + empleado.getNombre());
    }
}
