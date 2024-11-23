package com.cloudcomputing.erp.controllers.rh;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.services.EmpleadoService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class CrearEmpleadoController {
    private final EmpleadoService empleadoService;
    private EmpleadoDTO empleado;
    private String confirmPwd;

    public CrearEmpleadoController() {
        empleadoService = new EmpleadoService();
        empleado = new EmpleadoDTO();
        empleado.setDomicilio(new EmpleadoDTO.Domicilio());
        confirmPwd = "";
    }

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }
    
    public String crearEmpleado() {
        empleadoService.agregarEmpleado(empleado);
        return "dashboard.xhtml?faces-redirect=true";
    }
}
