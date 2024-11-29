package com.cloudcomputing.erp.controllers.rh;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.dto.RolDTO;
import com.cloudcomputing.erp.services.EmpleadoService;
import com.cloudcomputing.erp.services.RolService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Named
@ViewScoped
public class CrearEmpleadoController implements Serializable {
    private final EmpleadoService empleadoService;
    private final RolService rolService;
    private EmpleadoDTO empleado;
    private List<RolDTO> roles;

    public CrearEmpleadoController() {
        empleadoService = new EmpleadoService();
        rolService = new RolService();
        empleado = new EmpleadoDTO();
        empleado.setDomicilio(new EmpleadoDTO.Domicilio());
    }

    @PostConstruct
    public void init() {
        roles = rolService.obtenerRoles();
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

    public String crearEmpleado() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String password = String.format("%s-%s",
            empleado.getFechaAlta().format(formatter),
            empleado.getNombre());
    empleado.setPwd(password);
    empleadoService.agregarEmpleado(empleado);
    return "dashboard.xhtml?faces-redirect=true";
}
}
