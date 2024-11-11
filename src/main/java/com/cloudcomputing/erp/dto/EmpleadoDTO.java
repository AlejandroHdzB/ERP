package com.cloudcomputing.erp.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import java.util.Date;

@Data
@NoArgsConstructor
@Setter
@Getter
public class EmpleadoDTO {

    @SerializedName("idUser")
    private String idUser;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("apellidoPaterno")
    private String apellidoPaterno;

    @SerializedName("apellidoMaterno")
    private String apellidoMaterno;

    @SerializedName("email")
    private String email;

    @SerializedName("pwd")
    private String pwd;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("domicilio")
    private Domicilio domicilio;

    @SerializedName("fechaAlta")
    private Date fechaAlta;

    @SerializedName("fechaNacimiento")
    private Date fechaNacimiento;

    @SerializedName("estatus")
    private String estatus;

    @SerializedName("salario")
    private double salario;

    @SerializedName("nss")
    private String nss;

    @SerializedName("rfc")
    private String rfc;

    @SerializedName("noCuenta")
    private String noCuenta;

    @SerializedName("rol")
    private String rol;

    @Data
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Domicilio {

        @SerializedName("calle")
        private String calle;

        @SerializedName("noExt")
        private int noExt;

        @SerializedName("noInt")
        private int noInt;

        @SerializedName("cp")
        private int cp;

        @SerializedName("ciudad")
        private String ciudad;

        @SerializedName("estado")
        private String estado;
    }
}
