package com.cloudcomputing.erp.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VistaNominaGeneral {
    private String idNomina;
    private String idEmpleado;
    private String nombreEmpleado;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private LocalDate fechaPago;
    private double salarioBruto;
    private double deducciones;
    private double salarioNeto;
    private String documentoNomina;

}
