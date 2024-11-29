/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cloudcomputing.erp.controllers.comprasVentas.productos;

import com.cloudcomputing.erp.dto.CompraDTO;
import com.cloudcomputing.erp.services.CompraService;
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
public class ReporteInventarioController {
    private List<CompraDTO> listaCompras;
    private final CompraService compraService = new CompraService();

    @PostConstruct
    public void init() {
        listaCompras = compraService.obtenerCompras();
    }

    public List<CompraDTO> getListaCompras() {
        return listaCompras;
    }
}