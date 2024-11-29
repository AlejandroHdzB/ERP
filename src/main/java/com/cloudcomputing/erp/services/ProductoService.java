/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.dto.DimensionesDTO;
import com.cloudcomputing.erp.dto.ProductoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import com.cloudcomputing.erp.database.Connection;
import java.util.ArrayList;
import org.bson.Document;

@ApplicationScoped
public class ProductoService {

    // Simulación de la colección Producto_ERP
    private final List<ProductoDTO> productos = List.of(
        new ProductoDTO("EMP001", "Caja Pequeña",10.0, new DimensionesDTO(30, 20, 10), 10.0),
        new ProductoDTO("EMP002", "Caja Mediana",10.0, new DimensionesDTO(40, 30, 20), 20.0),
        new ProductoDTO("EMP003", "Caja Grande",10.0, new DimensionesDTO(60, 40, 30), 30.0)
    );

    public ProductoDTO determinarEmpaque(DimensionesDTO dimensiones, double peso) {
        Optional<ProductoDTO> empaqueAdecuado = productos.stream()
            .filter(producto -> cabeEnEmpaque(producto.getDimensiones(), dimensiones))
            .filter(producto -> peso <= producto.getCapacidad())
            .findFirst();

        return empaqueAdecuado.orElseThrow(() ->
            new IllegalArgumentException("No se encontró un empaque adecuado para las dimensiones y peso especificados."));
    }

    private boolean cabeEnEmpaque(DimensionesDTO dimensionesEmpaque, DimensionesDTO dimensionesPaquete) {
        return dimensionesPaquete.getAlto() <= dimensionesEmpaque.getAlto()
            && dimensionesPaquete.getAncho() <= dimensionesEmpaque.getAncho()
            && dimensionesPaquete.getLargo() <= dimensionesEmpaque.getLargo();
    }
    
    private final Connection dbConnection = new Connection();

    public ProductoService() {
        dbConnection.connect();
    }
    public boolean crearProducto(ProductoDTO productoDTO) {
        try {
            Document dimensiones = new Document("alto", productoDTO.getDimensiones().getAlto())
                    .append("ancho", productoDTO.getDimensiones().getAncho())
                    .append("largo", productoDTO.getDimensiones().getLargo());

            Document productoDocument = new Document("idProducto", productoDTO.getIdProducto())
                    .append("nombre", productoDTO.getNombre())
                    .append("precio", productoDTO.getPrecio())
                    .append("dimensiones", dimensiones)
                    .append("capacidad", productoDTO.getCapacidad());

            return dbConnection.addDocument("productos", productoDocument);
        } catch (Exception e) {
            System.err.println("Error al crear producto: " + e.getMessage());
            return false;
        }
    }
    
    public List<ProductoDTO> obtenerProductos() {
        List<ProductoDTO> listaProductos = new ArrayList<>();
        try {
            List<Document> documentos = dbConnection.getCollectionData("Producto_ERP");
            for (Document doc : documentos) {
                ProductoDTO producto = new ProductoDTO();
                producto.setIdProducto(doc.getString("idProducto"));
                producto.setNombre(doc.getString("nombre"));
                producto.setPrecio(doc.getDouble("precio"));
                producto.setCapacidad(doc.getDouble("capacidad"));
                
                // Dimensiones
                Document dimensionesDoc = (Document) doc.get("dimensiones");
                if (dimensionesDoc != null) {
                    producto.setDimensiones(new DimensionesDTO(
                        dimensionesDoc.getDouble("alto"),
                        dimensionesDoc.getDouble("ancho"),
                        dimensionesDoc.getDouble("largo")
                    ));
                }
                listaProductos.add(producto);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        }
        return listaProductos;
    }
}