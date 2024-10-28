package com.cloudcomputing.erp.dto;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import java.util.Date;

public class EmpleadoDTO {

    @JsonbProperty("idUser")
    private String idUser;

    @JsonbProperty("nombre")
    private String nombre;

    @JsonbProperty("apellidoPaterno")
    private String apellidoPaterno;

    @JsonbProperty("apellidoMaterno")
    private String apellidoMaterno;

    @JsonbProperty("email")
    private String email;

    @JsonbProperty("pwd")
    private String pwd;

    @JsonbProperty("telefono")
    private String telefono;

    @JsonbProperty("domicilio")
    private Domicilio domicilio;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonbProperty("fechaAlta")
    private Date fechaAlta;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @JsonbProperty("fechaNacimiento")
    private Date fechaNacimiento;

    @JsonbProperty("estatus")
    private String estatus;

    @JsonbProperty("salario")
    private double salario;

    @JsonbProperty("nss")
    private String nss;

    @JsonbProperty("rfc")
    private String rfc;

    @JsonbProperty("noCuenta")
    private String noCuenta;

    @JsonbProperty("rol")
    private String rol;

    // Clase interna para Domicilio
    public static class Domicilio {

        @JsonbProperty("calle")
        private String calle;

        @JsonbProperty("noExt")
        private int noExt;

        @JsonbProperty("noInt")
        private int noInt;

        @JsonbProperty("cp")
        private int cp;

        @JsonbProperty("ciudad")
        private String ciudad;

        @JsonbProperty("estado")
        private String estado;

        // Getters y Setters para Domicilio
        public String getCalle() {
            return calle;
        }

        public void setCalle(String calle) {
            this.calle = calle;
        }

        public int getNoExt() {
            return noExt;
        }

        public void setNoExt(int noExt) {
            this.noExt = noExt;
        }

        public int getNoInt() {
            return noInt;
        }

        public void setNoInt(int noInt) {
            this.noInt = noInt;
        }

        public int getCp() {
            return cp;
        }

        public void setCp(int cp) {
            this.cp = cp;
        }

        public String getCiudad() {
            return ciudad;
        }

        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }

    // Getters y Setters para EmpleadoDTO

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(String noCuenta) {
        this.noCuenta = noCuenta;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
