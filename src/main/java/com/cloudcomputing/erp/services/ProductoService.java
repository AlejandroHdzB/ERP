/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.dto.DimensionesDTO;
import com.cloudcomputing.erp.dto.ProductoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductoService {

    // Simulación de la colección Producto_ERP
    private final List<ProductoDTO> productos = List.of(
        new ProductoDTO("EMP001", "Caja Pequeña",10.0, new DimensionesDTO(30, 20, 10), 10.0),
        new ProductoDTO("EMP002", "Caja Mediana",10.0, new DimensionesDTO(40, 30, 20), 20.0),
        new ProductoDTO("EMP003", "Caja Grande",10.0, new DimensionesDTO(60, 40, 30), 30.0)
    );

    public ProductoDTO determinarEmpaque(DimensionesDTO dimensiones, double peso) {
        Optional<ProductoDTO> empaqueAdecuado = productos.stream()
            .filter(producto -> cabeEnEmpaque(producto.getDimensiones(), dimensiones))
            .filter(producto -> peso <= producto.getCapacidad())
            .findFirst();

        return empaqueAdecuado.orElseThrow(() ->
            new IllegalArgumentException("No se encontró un empaque adecuado para las dimensiones y peso especificados."));
    }

    private boolean cabeEnEmpaque(DimensionesDTO dimensionesEmpaque, DimensionesDTO dimensionesPaquete) {
        return dimensionesPaquete.getAlto() <= dimensionesEmpaque.getAlto()
            && dimensionesPaquete.getAncho() <= dimensionesEmpaque.getAncho()
            && dimensionesPaquete.getLargo() <= dimensionesEmpaque.getLargo();
    }
}