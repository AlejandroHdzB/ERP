/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudcomputing.erp.dto;

import java.time.LocalDate;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Data
public class DireccionDTO {
    private String pais;
    private String estado;
    private String ciudad;
    private String calle;
    private String numeroExterior;
    private String numeroInterior;
    private String colonia;
    private String codigoPostal;
}
