package com.cloudcomputing.erp.controllers.auth;

import com.cloudcomputing.erp.apiConnections.apiLoginEmpleados;
import com.cloudcomputing.erp.apiConnections.RespuestaLoginEmpleados;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
@RequestScoped
public class LoginController {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        RespuestaLoginEmpleados respuesta = apiLoginEmpleados.consultar(email, password);
        
        if (respuesta != null && respuesta.isSuccess()) {
            String rol = respuesta.getRole();

            Map<String, String> roleToPageMap = new HashMap<>();
            roleToPageMap.put("Ventas", "compras_y_ventas/Menu.xhtml");
            roleToPageMap.put("Recursos Humanos", "/rh/dashboard.xhtml");
            roleToPageMap.put("Contabilidad", "/contabilidad/dashboard.xhtml");
            roleToPageMap.put("Compras", "compras_y_ventas/Menu.xhtml");
            roleToPageMap.put("Administrador", "/admin/index.xhtml");

            String redirectPage = roleToPageMap.get(rol);

            if (redirectPage != null) {
                return redirectPage + "?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Rol de usuario no reconocido."));
                return null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Credenciales incorrectas."));
            return null;
        }
    }

}
