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
    private final String idUser;
    private EmpleadoDTO empleado;

    public EditarEmpleadoController() {
        empleadoService = new EmpleadoService();
        idUser = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idUser");
        System.out.println("ID del usuario: " + idUser);
        
        empleado = empleadoService.obtenerEmpleadoPorId(idUser);
    }

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    // Método para guardar los cambios
    public String guardarCambios() {
        // Lógica para actualizar el empleado
        System.out.println("Guardando cambios para: " + empleado.getNombre());
        return "listaEmpleados.xhtml?faces-redirect=true";
    }
}
