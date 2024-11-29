/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudcomputing.erp.controllers.comprasVentas.crm;

import com.cloudcomputing.erp.dto.CotizacionDTO;
import com.cloudcomputing.erp.dto.PedidoDTO;
import com.cloudcomputing.erp.services.PedidoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/crm/pedidos")
public class AutomatizacionPedidosController {

    @Inject
    private PedidoService pedidoService;

    @POST
    @Path("/cotizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cotizarPedido(PedidoDTO pedido) {
        try {
            // Procesar cotización
            CotizacionDTO cotizacion = pedidoService.cotizarPedido(pedido);

            // Retornar la cotización
            return Response.ok(cotizacion).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno al procesar la cotización").build();
        }
    }

    @POST
    @Path("/confirmar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response confirmarPedido(CotizacionDTO cotizacion) {
        try {
            System.out.println("Recibiendo solicitud para confirmar pedido desde CRM...");
            PedidoDTO pedido = pedidoService.confirmarPedido(cotizacion);
            System.out.println("Pedido confirmado: " + pedido);

            // Responder con el pedido confirmado
            return Response.status(Response.Status.CREATED).entity(pedido).build();
        } catch (IllegalArgumentException e) {
            System.err.println("Error en la solicitud: " + e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            System.err.println("Error interno: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error interno al confirmar el pedido").build();
        }
    }

}
