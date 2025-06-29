
package Controller;

import Dao.CursosAdminDAO;
import Model.Curso;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@MultipartConfig
@WebServlet("/CursosAdminController")
public class CursosAdminController extends HttpServlet {

    private CursosAdminDAO dao = new CursosAdminDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Curso> cursos = dao.obtenerCursos();
        req.setAttribute("listaCursos", cursos);
        req.setAttribute("listaPeriodos", dao.obtenerPeriodos());
        req.setAttribute("listaMaterias", dao.obtenerMaterias());
        req.setAttribute("listaSalones", dao.obtenerSalones());

        RequestDispatcher dispatcher = req.getRequestDispatcher("adminCursos.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        if ("crear".equals(accion)) {
            int periodoId = Integer.parseInt(req.getParameter("periodoId"));
            int materiaId = Integer.parseInt(req.getParameter("materiaId"));
            int salonId = Integer.parseInt(req.getParameter("salonId"));
            dao.crearCurso(materiaId, salonId, periodoId);
        }

        if ("eliminar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.eliminarCurso(id);
        }

        doGet(req, resp);
    }
}
