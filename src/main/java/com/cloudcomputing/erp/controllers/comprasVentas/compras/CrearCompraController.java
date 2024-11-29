/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cloudcomputing.erp.controllers.comprasVentas.compras;

import com.cloudcomputing.erp.dto.CompraDTO;
import com.cloudcomputing.erp.services.CompraService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/compras")
public class CrearCompraController {

    private final CompraService compraService = new CompraService();

    @POST
    @Path("/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearCompra(CompraDTO compraDTO) {
        try {
            compraService.crearCompra(compraDTO);
            return Response.status(Response.Status.CREATED).entity("Compra creada con éxito").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
