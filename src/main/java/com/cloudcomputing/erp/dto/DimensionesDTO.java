/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudcomputing.erp.dto;
 
import lombok.Data;
 
@Data
public class DimensionesDTO {
    private double alto;
    private double ancho;
    private double largo;
 
    // Constructor con parámetros
    public DimensionesDTO(double alto, double ancho, double largo) {
        this.alto = alto;
        this.ancho = ancho;
        this.largo = largo;
    }
}
