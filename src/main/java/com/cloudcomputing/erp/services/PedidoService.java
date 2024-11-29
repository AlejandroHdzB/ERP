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
            double costoAduanero = tarifaAduanera ? 50.0 : 0.0; // Tarifa fija más baja
            System.out.println("Tarifa aduanera aplicada: " + tarifaAduanera + " (Costo: " + costoAduanero + ")");

// Calcular distancia
            double distancia = googleMapsService.calcularDistancia(origen, destino);
            System.out.println("Distancia calculada (metros): " + distancia);

            double costoPorDistancia = (distancia / 1000) * 0.001; // Ajustado a un costo muy bajo por km
            System.out.println("Costo por distancia: " + costoPorDistancia);

            double tarifaBase = 50.0; // Tarifa base reducida
            System.out.println("Tarifa base: " + tarifaBase);

            double tarifaPeso = pedido.getPeso() * 1.0; // Tarifa reducida por peso
            System.out.println("Tarifa por peso: " + tarifaPeso);

            double subtotal = costoPorDistancia + tarifaPeso + tarifaBase + costoAduanero;
            System.out.println("Subtotal (antes del seguro): " + subtotal);

            double seguro = pedido.isFragil() ? pedido.getValorDeclarado() * 0.005 : 0.0; // Seguro al 0.5%
            System.out.println("Seguro aplicado: " + seguro);
            subtotal += seguro;
            System.out.println("Subtotal (incluyendo seguro): " + subtotal);

            double iva = subtotal * 0.16; // IVA sobre el subtotal ajustado
            System.out.println("IVA calculado: " + iva);

            double costoOperacion = subtotal + iva; // Subtotal + IVA
            System.out.println("Costo de operación: " + costoOperacion);

            double ganancia = subtotal * 0.05; // Ganancia reducida al 5%
            System.out.println("Ganancia calculada: " + ganancia);

            double costoTotal = costoOperacion + ganancia; // Costo total
            System.out.println("Costo total: " + costoTotal);

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
    System.out.println("Iniciando la confirmación del pedido...");

    // Validar datos de la cotización
    if (cotizacion == null) {
        throw new IllegalArgumentException("La cotización no puede ser nula.");
    }
    System.out.println("Cotización recibida: " + cotizacion);

    // Convertir cotización en un pedido
    PedidoDTO pedido = cotizacion.toPedidoDTO();
    System.out.println("Pedido generado a partir de la cotización: " + pedido);

    // Asignar un estatus inicial al pedido
    pedido.setEstatus("Confirmado"); // O cualquier otro estatus inicial
    System.out.println("Estatus del pedido: " + pedido.getEstatus());

    // Asignar fecha de pedido
    pedido.setFechaPedido(java.time.LocalDate.now());
    System.out.println("Fecha del pedido asignada: " + pedido.getFechaPedido());

    // Retornar el pedido generado
    System.out.println("Pedido confirmado con éxito.");
    return pedido;
}


    private void registrarPedidoEnBaseDeDatos(PedidoDTO pedido) {
        // Simulación de inserción en MongoDB
        System.out.println("Simulación: Guardando pedido en la base de datos MongoDB...");
        System.out.println("Pedido: " + pedido);
        // Aquí debes usar la lógica real para insertar el pedido en MongoDB
    }

    private void validarDatosPreliminares(PedidoDTO pedido) {
        if (pedido.getDetallesPaquete() == null || pedido.getPeso() <= 0) {
            throw new IllegalArgumentException("Dimensiones y peso son obligatorios.");
        }
        if (pedido.getOrigen() == null || pedido.getDestino() == null) {
            throw new IllegalArgumentException("Dirección de origen y destino son obligatorias.");
        }
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
