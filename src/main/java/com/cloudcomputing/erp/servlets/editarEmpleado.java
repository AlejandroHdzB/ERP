package com.cloudcomputing.erp.servlets;

import com.cloudcomputing.erp.dto.EmpleadoDTO;
import com.cloudcomputing.erp.services.EmpleadoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/editarEmpleado")
public class editarEmpleado extends HttpServlet {
    private static final Logger logger = Logger.getLogger(editarEmpleado.class.getName()); 
    private static final long serialVersionUID = 1L;
    private EmpleadoService empleadoService;

    @Override
    public void init() {
        // Instancia del servicio de empleado
        empleadoService = new EmpleadoService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el ID del usuario de la solicitud
        request.getParameterMap().forEach((key, value) -> {
        logger.info("Parámetro: " + key + " = " + String.join(", ", value));
        });

        // Obtener el ID del usuario de la solicitud
        String idUser = request.getParameter("idUser");
        logger.info("ID del usuario recibido: " + idUser);

        // Validar si el ID es válido
        if (idUser == null || idUser.isEmpty()) {
            response.sendRedirect("listaEmpleados"); // Redirige a la lista si no hay ID
            return;
        }

        // Buscar el empleado por ID usando el servicio
        EmpleadoDTO empleado = empleadoService.obtenerEmpleadoPorId(idUser);

        // Verificar si se encontró el empleado
        if (empleado == null) {
            response.sendRedirect("listaEmpleados"); // Redirige si el empleado no existe
            return;
        }
        logger.info(empleado.getNombre());
        // Pasar el objeto EmpleadoDTO a la JSP
        request.setAttribute("empleado", empleado);

        // Redirigir a la página editarEmpleado.jsp en la carpeta 'rh'
        request.getRequestDispatcher("/rh/editarEmpleado.jsp").forward(request, response);
    }
}
