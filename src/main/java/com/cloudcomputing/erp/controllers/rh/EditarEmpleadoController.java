package com.cloudcomputing.erp.controllers.rh;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.dto.RolDTO;
import com.cloudcomputing.erp.services.EmpleadoService;
import com.cloudcomputing.erp.services.RolService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class EditarEmpleadoController implements Serializable {

    private final EmpleadoService empleadoService;
    private final RolService rolService;
    private String idEmpleado;
    private EmpleadoDTO empleado;
    private List<RolDTO> roles;

    public EditarEmpleadoController() {
        empleadoService = new EmpleadoService();
        rolService = new RolService();
    }

    @PostConstruct
    public void init() {
        if (empleado != null && empleado.getIdEmpleado() != null) {
            return;
        }

        roles = rolService.obtenerRoles();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (facesContext != null) {
            idEmpleado = facesContext.getExternalContext().getRequestParameterMap().get("idEmpleado");

            if (idEmpleado != null && !idEmpleado.isEmpty()) {
                System.out.println("ID de empleado recibido en init(): " + idEmpleado);
                empleado = empleadoService.obtenerEmpleadoPorId(idEmpleado);
                if (empleado != null) {
                    empleado.setIdEmpleado(idEmpleado);
                    if (empleado.getDomicilio() == null) {
                        empleado.setDomicilio(new EmpleadoDTO.Domicilio());
                    }
                } else {
                    empleado = new EmpleadoDTO(); 
                }
            } else {
                System.err.println("ID de empleado no válido en init(): " + idEmpleado);
                empleado = new EmpleadoDTO(); 
            }
        }
    }

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    public List<RolDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RolDTO> roles) {
        this.roles = roles;
    }

    public String guardarCambios() {
        if (empleado == null || empleado.getIdEmpleado() == null || empleado.getIdEmpleado().isEmpty()) {
            System.err.println("Error: No se puede guardar el empleado sin un ID válido.");
            return null;
        }

        boolean resultado = empleadoService.actualizarEmpleado(empleado);
        if (resultado) {
            System.out.println("Cambios guardados correctamente.");
            return "listaEmpleados.xhtml?faces-redirect=true";
        } else {
            System.err.println("Error al guardar los cambios.");
            return null;
        }
    }


    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
}
