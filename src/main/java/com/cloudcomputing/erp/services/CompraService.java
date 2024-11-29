/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.dto.CompraDTO;
import com.cloudcomputing.erp.database.Connection;
import org.bson.Document;

public class CompraService {

    private final Connection connection;

    public CompraService() {
        this.connection = new Connection();
        this.connection.connect(); // Aseguramos la conexión a la base de datos
    }

    public void crearCompra(CompraDTO compraDTO) {
        // Crear el documento para la compra
        Document compraDoc = new Document()
                .append("idcompra", compraDTO.getIdcompra())
                .append("idproducto", compraDTO.getIdproducto())
                .append("fechacompra", compraDTO.getFechacompra().toString())
                .append("montoTotal", compraDTO.getMontoTotal())
                .append("detalleproducto", new Document()
                        .append("cantidad", compraDTO.getDetalleproducto().getCantidad())
                        .append("precio_unitario", compraDTO.getDetalleproducto().getPrecio_unitario()));

        // Usar la conexión para agregar el documento
        boolean success = connection.addDocument("compras", compraDoc);
        if (!success) {
            throw new RuntimeException("Error al agregar la compra a la base de datos");
        }
    }
}

