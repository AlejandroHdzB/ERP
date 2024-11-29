/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cloudcomputing.erp.controllers.comprasVentas.productos;

import com.cloudcomputing.erp.dto.ProductoDTO;
import com.cloudcomputing.erp.services.ProductoService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/productos")
public class AltaProductoController {

    private final ProductoService productoService = new ProductoService();

    @POST
    @Path("/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearProducto(ProductoDTO productoDTO) {
        try {
            productoService.crearProducto(productoDTO);
            return Response.status(Response.Status.CREATED).entity("Producto creado con éxito").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
