
package com.cloudcomputing.erp.beans;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.services.EmpleadoService;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author Alejandro
 */
@WebServlet(name = "listaEmpleados", urlPatterns = {"/listaEmpleados"})
public class listaEmpleados extends HttpServlet {
    
    private final EmpleadoService empleadoService = new EmpleadoService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<EmpleadoDTO> empleados = empleadoService.listarEmpleados();
        
        request.setAttribute("empleados", empleados);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/rh/listaEmpleados.jsp");
        dispatcher.forward(request, response);
    }
    
    
}
