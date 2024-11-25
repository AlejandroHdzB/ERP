package com.cloudcomputing.erp.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Setter
@Getter
public class EmpleadoDTO {

    @SerializedName("_id")
    private String idEmpleado;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("apellido_paterno")
    private String apellidoPaterno;

    @SerializedName("apellido_materno")
    private String apellidoMaterno;

    @SerializedName("email")
    private String email;

    @SerializedName("pwd")
    private String pwd;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("domicilio")
    private Domicilio domicilio;

    @SerializedName("fecha_alta")
    private LocalDate fechaAlta;

    @SerializedName("fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @SerializedName("estatus")
    private String estatus;

    @SerializedName("salario")
    private double salario;

    @SerializedName("NSS")
    private String nss;

    @SerializedName("RFC")
    private String rfc;

    @SerializedName("No_Cuenta")
    private String noCuenta;

    @SerializedName("ROL")
    private String rol;

    @Data
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Domicilio {

        @SerializedName("calle")
        private String calle;

        @SerializedName("no_Ext")
        private int noExt;

        @SerializedName("no_Int")
        private int noInt;

        @SerializedName("CP")
        private int cp;

        @SerializedName("ciudad")
        private String ciudad;

        @SerializedName("estado")
        private String estado;
    }
}
