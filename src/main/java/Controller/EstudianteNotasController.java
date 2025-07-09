
package Controller;

import Dao.EstudianteNotasDAO;
import Model.AsignaturaNota;
import Model.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/EstudianteNotasController")
public class EstudianteNotasController extends HttpServlet {

    private final EstudianteNotasDAO notasDAO = new EstudianteNotasDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null && usuario.getRol().equals("estudiante")) {
            int usuarioId = usuario.getId();

            // Nuevo: obtener estudianteId real
            int estudianteId = notasDAO.obtenerEstudianteIdPorUsuarioId(usuarioId);

            List<AsignaturaNota> listaNotas = notasDAO.obtenerNotasPorEstudiante(estudianteId);
            request.setAttribute("listaNotas", listaNotas);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("estudianteNotas.jsp");
        dispatcher.forward(request, response);
    }

}
