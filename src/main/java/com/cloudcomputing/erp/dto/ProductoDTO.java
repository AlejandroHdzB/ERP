/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cloudcomputing.erp.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductoDTO {
    private String idInventario;
    private double cantidadDisponible;
    private String unidadMedida;
    private String moneda;
    private double reordenMinimo;
    private int tipoInsumo;
    private DetallesEmpaque detallesEmpaque;
    private Proveedor proveedor;
    private LocalDate fechaUltimaActualizacion;
}