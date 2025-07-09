
package Controller;

import Dao.DocenteDAO;
import Dao.UsuarioDAO;
import Model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ResumenDocenteController")
public class ResumenDocenteController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la sesión y el objeto Usuario guardado en Login
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuario") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Verificar que sea rol docente
        if (!"docente".equals(usuario.getRol())) {
            response.sendRedirect("login.jsp");
            return;
        }

        int usuarioId = usuario.getId();

        try {
            // Obtener el id del docente a partir del id del usuario
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Integer docenteId = usuarioDAO.obtenerIdDocentePorIdUsuario(usuarioId);

            if (docenteId == null) {
                // No está registrado como docente
                request.setAttribute("asignaturasAsignadas", 0);
                request.setAttribute("cursosActivos", 0);
                request.getRequestDispatcher("resumenDocente.jsp").forward(request, response);
                return;
            }

            // Consultar asignaturas y cursos activos
            DocenteDAO docenteDAO = new DocenteDAO();
            int asignaturasAsignadas = docenteDAO.contarAsignaturas(docenteId);
            int cursosActivos = docenteDAO.contarCursosActivos(docenteId);

            // Pasar datos al JSP
            request.setAttribute("asignaturasAsignadas", asignaturasAsignadas);
            request.setAttribute("cursosActivos", cursosActivos);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("asignaturasAsignadas", 0);
            request.setAttribute("cursosActivos", 0);
        }

        request.getRequestDispatcher("resumenDocente.jsp").forward(request, response);
    }
    
}
