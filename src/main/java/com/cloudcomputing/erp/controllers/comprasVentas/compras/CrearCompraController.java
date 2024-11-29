/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudcomputing.erp.controllers.comprasVentas.compras;

import com.cloudcomputing.erp.dto.CompraDTO;
import com.cloudcomputing.erp.services.CompraService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/compras")
public class CrearCompraController {
    private CompraDTO compraDTO = new CompraDTO();
    private final CompraService compraService = new CompraService();
    
    public CompraDTO getCompraDTO() {
        return compraDTO;
    }

    public void setCompraDTO(CompraDTO compraDTO) {
        this.compraDTO = compraDTO;
    }

    public String crearCompra() {
        if (compraService.crearCompra(compraDTO)) {
            return "../Menu.xhtml"; // Redirigir a página de éxito
        } else {
            return "../Menu.xhtml"; // Redirigir a página de error
        }
    }
    
    @POST
    @Path("/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearCompra(CompraDTO compraDTO) {
        try {
            boolean result = compraService.crearCompra(compraDTO);
            if (result) {
                return Response.status(Response.Status.CREATED).entity("Compra creada correctamente").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Error al crear la compra").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error del servidor: " + e.getMessage()).build();
        }
    }
}

