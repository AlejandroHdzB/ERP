/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.database.Connection;
import com.cloudcomputing.erp.dto.CompraDTO;
import com.cloudcomputing.erp.dto.DetallesPoductoDTO;
import org.bson.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompraService {

    private final Connection dbConnection = new Connection();

    public CompraService() {
        dbConnection.connect();
    }

    public boolean crearCompra(CompraDTO compraDTO) {
        try {
            // Obtener detalles del producto desde la base de datos
            Document producto = dbConnection.getDocumentById(compraDTO.getIdproducto(), "productos");

            if (producto == null) {
                System.err.println("Producto no encontrado");
                return false;
            }

            double precioUnitario = producto.getDouble("precio_unitario");
            double montoTotal = precioUnitario * compraDTO.getDetalleproducto().getCantidad();
            compraDTO.setMontoTotal(montoTotal);
            compraDTO.setFechacompra(LocalDate.now());

            // Crear documento para la colección "compras"
            Document compraDocument = new Document("idcompra", compraDTO.getIdcompra())
                    .append("idproducto", compraDTO.getIdproducto())
                    .append("fechacompra", compraDTO.getFechacompra().toString())
                    .append("montoTotal", montoTotal)
                    .append("detalleproducto", new Document("cantidad", compraDTO.getDetalleproducto().getCantidad()));

            return dbConnection.addDocument("compras", compraDocument);
        } catch (Exception e) {
            System.err.println("Error al crear compra: " + e.getMessage());
            return false;
        }
    }
    
    public List<CompraDTO> obtenerCompras() {
        List<CompraDTO> listaCompras = new ArrayList<>();
        try {
            List<Document> documentos = dbConnection.getCollectionData("Compras");
            for (Document doc : documentos) {
                CompraDTO compra = new CompraDTO();
                compra.setIdcompra(doc.getString("idcompra"));
                compra.setIdproducto(doc.getString("idproducto"));
                compra.setFechacompra(LocalDate.parse(doc.getString("fechacompra")));
                compra.setMontoTotal(doc.getDouble("montoTotal"));

                // Detalle del producto
                Document detalleDoc = (Document) doc.get("detalleproducto");
                if (detalleDoc != null) {
                    DetallesPoductoDTO detalleProducto = new DetallesPoductoDTO();
                    detalleProducto.setCantidad(detalleDoc.getInteger("cantidad"));
                    compra.setDetalleproducto(detalleProducto);
                }
                listaCompras.add(compra);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener compras: " + e.getMessage());
        }
        return listaCompras;
    }
}


