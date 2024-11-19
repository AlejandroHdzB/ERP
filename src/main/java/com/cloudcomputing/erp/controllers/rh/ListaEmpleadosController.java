package com.cloudcomputing.erp.controllers.rh;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.services.EmpleadoService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ListaEmpleadosController implements Serializable {
    private final EmpleadoService empleadoService;
    private List<EmpleadoDTO> empleados;
    
    public ListaEmpleadosController() {
        empleadoService = new EmpleadoService();
        empleados = empleadoService.listarEmpleados();

        if (empleados == null) {
            empleados = new ArrayList<>();
        }else {
            empleados.stream().forEach(empleado -> System.out.print(empleado.getIdEmpleado()+ empleado.getNombre()));
        }
    }



    public List<EmpleadoDTO> getEmpleados() {
        return empleados;
    }

    public void generarNomina(EmpleadoDTO empleado) {
        // Lógica para generar nómina
        System.out.println("Generando nómina para: " + empleado.getNombre());
    }

    public void eliminarEmpleado(EmpleadoDTO empleado) {
        // Lógica para eliminar empleado
        empleados.remove(empleado);
        System.out.println("Empleado eliminado: " + empleado.getNombre());
    }

    public String editarEmpleado(String idUser) {
        // Redirigir a la página de edición con el ID del empleado
        return "editarEmpleado.xhtml?idUser=" + idUser + "&faces-redirect=true";
    }
}
