/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.dto.CotizacionDTO;
import com.cloudcomputing.erp.dto.DireccionDTO;
import com.cloudcomputing.erp.dto.PedidoDTO;
import com.cloudcomputing.erp.dto.ProductoDTO;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.List;

@Stateless
public class PedidoService {

    @Inject
    private ProductoService productoService; // Servicio para consultar empaques

    @Inject
    private GoogleMapsService googleMapsService;

    public CotizacionDTO cotizarPedido(PedidoDTO pedido) {
        validarDatosPreliminares(pedido);
        
            // Validar direcciones de origen y destino
    validarDireccion(pedido.getOrigen(), "origen");
    validarDireccion(pedido.getDestino(), "destino");
        
        
        try {
            // Formatear direcciones
            String origen = formatearDireccion(pedido.getOrigen());
            String destino = formatearDireccion(pedido.getDestino());
            // Determinar el empaque adecuado CHECAR
            ProductoDTO empaque = productoService.determinarEmpaque(pedido.getDetallesPaquete().getDimensiones(), pedido.getPeso());

            // Validar si aplica tarifa aduanera
            boolean tarifaAduanera = !pedido.getOrigen().getPais().equalsIgnoreCase(pedido.getDestino().getPais());
            double costoAduanero = tarifaAduanera ? 100.0 : 0.0; // Tarifa fija

            // Calcular distancia
            double distancia = googleMapsService.calcularDistancia(origen, destino);

            // Calcular costos
            double tarifaBase = 50.0;
            double tarifaPeso = pedido.getPeso() * 10.0;
            double subtotal = (distancia * 0.05) + tarifaPeso + tarifaBase + costoAduanero;
            
               // Seguro (opcional)         
            double seguro = pedido.isFragil() ? pedido.getValorDeclarado() * 0.01 : 0.0;
            subtotal += seguro;

            double iva = subtotal * 0.16;
            double costoOperacion = subtotal + iva;
            double ganancia = subtotal * 0.1; // 10% del subtotal
            double costoTotal = costoOperacion + ganancia;

            // Crear cotización
            CotizacionDTO cotizacion = new CotizacionDTO();
            cotizacion.setSubtotal(subtotal);
            cotizacion.setCostoOperacion(costoOperacion);
            cotizacion.setGanancia(ganancia);
            cotizacion.setCostoTotal(costoTotal);
            cotizacion.setEmpaque(empaque);
            cotizacion.setTarifaAduanera(costoAduanero);
            cotizacion.setSeguro(seguro);

            return cotizacion;
        } catch (IOException e) {
            throw new IllegalStateException("Error al calcular la distancia", e);
        }
    }

    public PedidoDTO confirmarPedido(CotizacionDTO cotizacion) {
        PedidoDTO pedido = cotizacion.toPedidoDTO(); // Convertir cotización a pedido
        registrarPedidoEnBaseDeDatos(pedido);
        return pedido;
    }

    private void validarDatosPreliminares(PedidoDTO pedido) {
        if (pedido.getDetallesPaquete() == null || pedido.getPeso() <= 0) {
            throw new IllegalArgumentException("Dimensiones y peso son obligatorios.");
        }
        if (pedido.getOrigen() == null || pedido.getDestino() == null) {
            throw new IllegalArgumentException("Dirección de origen y destino son obligatorias.");
        }
    }

    private void registrarPedidoEnBaseDeDatos(PedidoDTO pedido) {
        // Guardar el pedido en la base de datos
        System.out.println("Pedido registrado: " + pedido.getIdPedido());
    }

    private String formatearDireccion(DireccionDTO direccion) {
        return String.format("%s,%s,%s", direccion.getCiudad(), direccion.getEstado(), direccion.getPais());
    }

    private void validarDireccion(DireccionDTO direccion, String tipo) {
    if (direccion == null) {
        throw new IllegalArgumentException("La dirección de " + tipo + " no puede ser nula.");
    }
    if (direccion.getCiudad() == null || direccion.getCiudad().isEmpty()) {
        throw new IllegalArgumentException("La ciudad de " + tipo + " es obligatoria.");
    }
    if (direccion.getEstado() == null || direccion.getEstado().isEmpty()) {
        throw new IllegalArgumentException("El estado de " + tipo + " es obligatorio.");
    }
    if (direccion.getPais() == null || direccion.getPais().isEmpty()) {
        throw new IllegalArgumentException("El país de " + tipo + " es obligatorio.");
    }
}

    
}
