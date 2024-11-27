/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cloudcomputing.erp.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PedidoDTO {
    private String idPedido;
    private LocalDate fechaPedido;
    private String estatus;
    private double costoOperacion;
    private double subtotal;
    private double ganancia;
    private double costoTotal;
    private DetallesPaqueteDTO detallesPaquete;
    private double peso;
    private String contenido;
    private double valorDeclarado;
    private boolean fragil;
    private SeguroDTO seguro;
    private DireccionDTO origen;
    private DireccionDTO destino;
    private boolean internacional;
    private List<ImpuestoDTO> impuestos;
}
