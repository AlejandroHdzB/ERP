/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudcomputing.erp.dto;
 
import lombok.Data;
 
@Data
public class CotizacionDTO {
    private double subtotal;
    private double costoOperacion;
    private double ganancia;
    private double costoTotal;
    private ProductoDTO empaque;
    private double tarifaAduanera;
    private double seguro;
 
    public PedidoDTO toPedidoDTO() {
        PedidoDTO pedido = new PedidoDTO();
        pedido.setSubtotal(subtotal);
        pedido.setCostoOperacion(costoOperacion);
        pedido.setGanancia(ganancia);
        pedido.setCostoTotal(costoTotal);
        pedido.setDetallesPaquete(empaque.toDetallesPaqueteDTO());
        return pedido;
    }
}
