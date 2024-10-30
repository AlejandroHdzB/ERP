/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.dto.ContabilidadDTO;
import com.cloudcomputing.erp.utils.ContabilidadMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class ContabilidadService {
     private static final Logger logger = Logger.getLogger(ContabilidadService.class.getName());
    public List<ContabilidadDTO> listarTransacciones() {
        List<ContabilidadDTO> trans = ContabilidadMapper.obtenerTransDesdeJson();
        return (trans == null) ? new ArrayList() : trans;
    }
}
