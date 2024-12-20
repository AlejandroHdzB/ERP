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
public class VentaDTO {
    private String idventa;
    private String idcliente;
    private String idpedido;
    private LocalDate fechaventa;
    private double montoTotal;
    private int estatus;
    private String metodopago;
    private String estatuspago;
    private String moneda;
}