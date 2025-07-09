
package Controller;

import Dao.EstudianteDAO;
import Model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/ResumenEstudianteController")
public class ResumenEstudianteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario u = (Usuario) session.getAttribute("usuario");

        if (u == null || !u.getRol().equalsIgnoreCase("estudiante")) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            EstudianteDAO estudianteDAO = new EstudianteDAO();

            String salonActual = estudianteDAO.obtenerSalonActual(u.getId());
            request.setAttribute("salonActual", salonActual);

            Map<String, List<String>> evaluacionesAgrupadas = estudianteDAO.obtenerEvaluacionesAgrupadas(u.getId());
            request.setAttribute("evaluacionesAgrupadas", evaluacionesAgrupadas);

            request.getRequestDispatcher("resumenEstudiante.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al obtener datos del estudiante", e);
        }
    }
}
