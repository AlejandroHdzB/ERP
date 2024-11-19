
package com.cloudcomputing.erp.controllers.contabilidad;

import com.cloudcomputing.erp.dto.ContabilidadDTO;
import com.cloudcomputing.erp.services.ContabilidadService;
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
@WebServlet(name = "listaTransacciones", urlPatterns = {"/listaTransacciones"})
public class listaTransacciones extends HttpServlet {
    
    private final ContabilidadService contabilidadService = new ContabilidadService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<ContabilidadDTO> trans = contabilidadService.listarTransacciones();
        
        request.setAttribute("trans", trans);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/contabilidad/listaTransacciones.jsp");
        dispatcher.forward(request, response);
    }
    
    
}
