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
import java.util.List;
 
@Stateless
public class PedidoService {
 
    @Inject
    private ProductoService productoService; // Servicio para consultar empaques
 
    public CotizacionDTO cotizarPedido(PedidoDTO pedido) {
        validarDatosPreliminares(pedido);
 
        // Determinar el empaque adecuado CHECAR
        ProductoDTO empaque = productoService.determinarEmpaque(pedido.getDetallesPaquete().getDimensiones(),pedido.getPeso());
 
        // Validar si aplica tarifa aduanera
        boolean tarifaAduanera = !pedido.getOrigen().getPais().equalsIgnoreCase(pedido.getDestino().getPais());
        double costoAduanero = tarifaAduanera ? 100.0 : 0.0; // Tarifa fija
 
        // Calcular distancia entre origen y destino
        double distancia = calcularDistancia(pedido.getOrigen(), pedido.getDestino());
 
        // Calcular costos
        double tarifaBase = 50.0;
        double tarifaPeso = pedido.getPeso() * 10.0;
        double subtotal = (distancia * 0.05) + tarifaPeso + tarifaBase + costoAduanero;
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
 
    private double calcularDistancia(DireccionDTO origen, DireccionDTO destino) {
        // Implementación para usar la API de Google Maps y calcular la distancia
        // Simulación:
        return 1000.0; // Distancia en metros
    }
 
    private void registrarPedidoEnBaseDeDatos(PedidoDTO pedido) {
        // Guardar el pedido en la base de datos
        System.out.println("Pedido registrado: " + pedido.getIdPedido());
    }
}
