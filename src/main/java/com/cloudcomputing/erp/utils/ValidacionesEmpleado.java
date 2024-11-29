package com.cloudcomputing.erp.utils;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import java.util.regex.Pattern;

public class ValidacionesEmpleado {

    // Validar solo texto (nombre, apellidos)
    public static boolean validarSoloTexto(String valor, String campo) {
        if (valor == null || valor.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", campo + " es requerido."));
            return false;
        }
        if (!Pattern.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", valor)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", campo + " solo debe contener letras."));
            return false;
        }
        return true;
    }

    // Validar email
    public static boolean validarEmail(String email) {
        if (email == null || email.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Correo electrónico es requerido."));
            return false;
        }
        if (!Pattern.matches("^[\\w._%+-]+@ittexpress\\.com$", email)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Correo electrónico debe ser válido y con dominio @ittexpress.com."));
            return false;
        }
        return true;
    }

    // Validar teléfono (10 dígitos)
    public static boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Teléfono es requerido."));
            return false;
        }
        if (!Pattern.matches("^\\d{10}$", telefono)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Teléfono debe tener 10 dígitos."));
            return false;
        }
        return true;
    }

    // Validar RFC
    public static boolean validarRFC(String rfc) {
        if (rfc == null || rfc.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "RFC es requerido."));
            return false;
        }
        if (!Pattern.matches("^[A-ZÑ&]{4}\\d{6}[A-Z0-9]{3}$", rfc)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "RFC debe tener un formato válido."));
            return false;
        }
        return true;
    }

    // Validar NSS
    public static boolean validarNSS(String nss) {
        if (nss == null || nss.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "NSS es requerido."));
            return false;
        }
        if (!Pattern.matches("^\\d{11}$", nss)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "NSS debe tener 11 dígitos."));
            return false;
        }
        return true;
    }

    // Validar número de cuenta
    public static boolean validarNumeroCuenta(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Número de cuenta es requerido."));
            return false;
        }
        if (!Pattern.matches("^\\d{10,18}$", numeroCuenta)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Número de cuenta debe tener entre 10 y 18 dígitos."));
            return false;
        }
        return true;
    }
}
