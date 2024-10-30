package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.utils.EmpleadoMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EmpleadoService {
       private static final Logger logger = Logger.getLogger(EmpleadoService.class.getName()); 
    public List<EmpleadoDTO> listarEmpleados() {
        EmpleadoMapper mapper = new EmpleadoMapper();
        List<EmpleadoDTO> empleados = mapper.obtenerEmpleadosDesdeJson();

        return (empleados == null) ? new ArrayList() : empleados;
    }

    public EmpleadoDTO obtenerEmpleadoPorId(String idUser) {
        EmpleadoMapper mapper = new EmpleadoMapper();
        List<EmpleadoDTO> empleados = mapper.obtenerEmpleadosDesdeJson();

        EmpleadoDTO emp = null;

        // Recorrer la lista de empleados para buscar el ID especificado
        for (EmpleadoDTO empleado : empleados) {
            if (empleado.getIdUser().equals(idUser)) {
                emp = empleado;
                break;
            }
        }
        logger.info(emp.getNombre());
        return emp; // Retornar el empleado encontrado o null si no existe
    }

    
    
}
