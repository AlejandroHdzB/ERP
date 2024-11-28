/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cloudcomputing.erp.dto;

import com.cloudcomputing.erp.dto.DetallesPaqueteDTO;
import com.cloudcomputing.erp.dto.DimensionesDTO;
import lombok.Data;
 
@Data
public class ProductoDTO {
    private String idProducto;
    private String nombre;
    private double precio;
    private DimensionesDTO dimensiones;
    private double capacidad;
 
    public DetallesPaqueteDTO toDetallesPaqueteDTO() {
        DetallesPaqueteDTO detallesPaquete = new DetallesPaqueteDTO();
        detallesPaquete.setIdPaquete(this.idProducto);
        detallesPaquete.setDimensiones(this.dimensiones);
        return detallesPaquete;
    }
}