
package Controller;

import Dao.ResumenAdminDAO;
import Model.LogActividad;
import Model.ResumenAdmin;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ResumenAdminController", urlPatterns = {"/ResumenAdminController"})
public class ResumenAdminController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ResumenAdminDAO resumenDAO = null;
        try {
            resumenDAO = new ResumenAdminDAO();
            
            // Obtener datos para el resumen
            ResumenAdmin resumen = resumenDAO.obtenerResumenAdmin();
            
            // Obtener logs recientes (últimos 10)
            List<LogActividad> logs = resumenDAO.obtenerLogsRecientes(10);
            
            // Registrar la visita al dashboard
            String ip = request.getRemoteAddr();
            Integer usuarioId = (Integer) request.getSession().getAttribute("usuarioId");
            if (usuarioId != null) {
                resumenDAO.registrarActividad(
                    usuarioId,
                    "VISITA_DASHBOARD",
                    "El administrador ha accedido al panel de control",
                    ip
                );
            }
            
            // Enviar datos a la vista
            request.setAttribute("resumen", resumen);
            request.setAttribute("logs", logs);
            
            request.getRequestDispatcher("/resumenAdmin.jsp").forward(request, response);
        } finally {
            if (resumenDAO != null) {
                resumenDAO.close();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
