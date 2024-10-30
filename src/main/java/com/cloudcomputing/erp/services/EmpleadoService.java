package com.cloudcomputing.erp.services;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.utils.EmpleadoMapper;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoService {
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
                break; // Salir del bucle si se encuentra el empleado
            }
        }

        return emp; // Retornar el empleado encontrado o null si no existe
    }

    
    
}
