package com.cloudcomputing.erp.controllers.rh;

import com.cloudcomputing.erp.apiConnections.ApiValidacionEmpleados;
import com.cloudcomputing.erp.apiConnections.RespuestaValidacionEmpleados;
import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.dto.RolDTO;
import com.cloudcomputing.erp.services.EmpleadoService;
import com.cloudcomputing.erp.services.RolService;
import com.cloudcomputing.erp.utils.ValidacionesEmpleado;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
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
        // Validaciones antes de crear empleado
        if (!validarCamposEmpleado()) {
            // Si alguna validación falla, se detiene la creación y se muestra un mensaje de error
            return null;
        }

        // Crear contraseña automática basada en fecha de alta y nombre
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String password = String.format("%s-%s",
                empleado.getFechaAlta().format(formatter),
                empleado.getNombre());
        empleado.setPwd(password);

        // Consultar si el empleado ya está registrado mediante API
        RespuestaValidacionEmpleados respuesta = ApiValidacionEmpleados.consultar(empleado.getEmail());
        if (respuesta != null) {
            if (respuesta.isSuccess()) {
                empleadoService.agregarEmpleado(empleado);
                return "dashboard.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", respuesta.getMessage()));
                return null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en la respuesta de la API de validación"));
            return null;
        }
    }

    private boolean validarCamposEmpleado() {
        if (!ValidacionesEmpleado.validarSoloTexto(empleado.getNombre(), "Nombre")) return false;
        if (!ValidacionesEmpleado.validarSoloTexto(empleado.getApellidoPaterno(), "Apellido Paterno")) return false;
        if (!ValidacionesEmpleado.validarEmail(empleado.getEmail())) return false;
        if (!ValidacionesEmpleado.validarTelefono(empleado.getTelefono())) return false;
        if (!ValidacionesEmpleado.validarRFC(empleado.getRfc())) return false;
        if (!ValidacionesEmpleado.validarNSS(empleado.getNss())) return false;
        if (!ValidacionesEmpleado.validarNumeroCuenta(empleado.getNoCuenta())) return false;

        return true;
    }
}
