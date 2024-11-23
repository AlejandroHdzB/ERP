package com.cloudcomputing.erp.controllers.auth;

import com.cloudcomputing.erp.apiConnections.ApiLoginEmpleados;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import com.cloudcomputing.erp.apiConnections.ResultadoRespuesta;
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
        ApiLoginEmpleados apiLoginEmpleados = new ApiLoginEmpleados();

        ResultadoRespuesta response = apiLoginEmpleados.consultar(email, password);

        System.out.println(response.getMensaje());

        if (response.getEstado() == 200) {
            String rol = response.getMensaje();

            Map<String, String> roleToPageMap = new HashMap<>();
            roleToPageMap.put("Ventas", "/ventas/index.xhtml");
            roleToPageMap.put("Recursos Humanos", "/rh/dashboard.xhtml");
            roleToPageMap.put("Contabilidad", "/contabilidad/index.xhtml");
            roleToPageMap.put("Compras", "/compras/index.xhtml");
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
