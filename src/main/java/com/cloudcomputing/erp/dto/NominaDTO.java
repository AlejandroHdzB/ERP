package com.cloudcomputing.erp.dto;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class NominaDTO {

    @SerializedName("_id")
    private String idNomina;

    @SerializedName("id_empleado")
    private String idEmpleado;

    @SerializedName("fecha_pago")
    private LocalDate fechaPago;

    @SerializedName("salario_bruto")
    private double salarioBruto;

    @SerializedName("deducciones")
    private double deducciones;

    @SerializedName("salario_neto")
    private double salarioNeto;

    @SerializedName("documento_nomina")
    private String documentoNomina;
}
