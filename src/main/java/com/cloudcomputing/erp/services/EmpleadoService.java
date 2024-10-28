package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.utils.EmpleadoMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EmpleadoService {
    private static final Logger logger = Logger.getLogger(EmpleadoService.class.getName());
    public List<EmpleadoDTO> listarEmpleados() {
        List<EmpleadoDTO> empleados = EmpleadoMapper.obtenerEmpleadosDesdeJson();
        return (empleados == null) ? new ArrayList() : empleados;
    }
}
