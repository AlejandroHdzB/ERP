/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cloudcomputing.erp.controllers.comprasVentas.compras;

import com.cloudcomputing.erp.dto.ProductoDTO;
import com.cloudcomputing.erp.services.ProductoService;
import jakarta.annotation.ManagedBean;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import java.util.List;

/**
 *
 * @author wolf_
 */
@ManagedBean
@ViewScoped
public class ReporteComprasController {
    private List<ProductoDTO> listaProductos;
    private final ProductoService productoService = new ProductoService();

    @PostConstruct
    public void init() {
        listaProductos = productoService.obtenerProductos();
    }

    public List<ProductoDTO> getListaProductos() {
        return listaProductos;
    }
}