/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudcomputing.erp.controllers.comprasVentas.productos;

import com.cloudcomputing.erp.dto.ProductoDTO;
import com.cloudcomputing.erp.services.ProductoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.cloudcomputing.erp.dto.ProductoDTO;

@Path("/productos")
public class AltaProductoController {
    private ProductoDTO productoDTO = new ProductoDTO();
    private final ProductoService productoService = new ProductoService();
        public ProductoDTO getProductoDTO() {
        return productoDTO;
    }

    public void setProductoDTO(ProductoDTO productoDTO) {
        this.productoDTO = productoDTO;
    }

    public String crearProducto() {
        if (productoService.crearProducto(productoDTO)) {
            return "../Menu.xhtml"; // Redirigir a página de éxito
        } else {
            return "../Menu.xhtml"; // Redirigir a página de error
        }
    }
    
    @POST
    @Path("/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearProducto(ProductoDTO productoDTO) {
        try {
            boolean result = productoService.crearProducto(productoDTO);
            if (result) {
                return Response.status(Response.Status.CREATED).entity("Producto creado correctamente").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Error al crear el producto").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error del servidor: " + e.getMessage()).build();
        }
    }
}

