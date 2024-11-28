/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cloudcomputing.erp.services;
 
import com.cloudcomputing.erp.dto.DimensionesDTO;
import com.cloudcomputing.erp.dto.ProductoDTO;
import jakarta.ejb.Stateless;
 
@Stateless
public class ProductoService {

    public ProductoDTO determinarEmpaque(DimensionesDTO dimensiones) {
        // Implementa la lógica para determinar el empaque
        return new ProductoDTO(); // Devuelve un objeto simulado por ahora

    }
}