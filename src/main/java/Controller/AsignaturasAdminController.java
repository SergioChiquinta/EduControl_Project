
package Controller;

import Dao.AsignaturasAdminDAO;
import Model.Asignatura;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@MultipartConfig
@WebServlet("/AsignaturasAdminController")
public class AsignaturasAdminController extends HttpServlet {
    private AsignaturasAdminDAO dao = new AsignaturasAdminDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cargarDatos(req);
        req.getRequestDispatcher("adminAsignaturas.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        if ("crear".equals(accion)) {
            String nombre = req.getParameter("nombre");
            int docenteId = Integer.parseInt(req.getParameter("docenteId"));
            dao.crearAsignatura(nombre, docenteId);
        }

        if ("editar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String nombre = req.getParameter("nombre");
            int docenteId = Integer.parseInt(req.getParameter("docenteId"));
            dao.editarAsignatura(id, nombre, docenteId);
        }

        if ("eliminar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.eliminarAsignatura(id);
        }

        cargarDatos(req);
        req.getRequestDispatcher("adminAsignaturas.jsp").forward(req, resp);
    }

    private void cargarDatos(HttpServletRequest req) {
        List<Asignatura> asignaturas = dao.obtenerAsignaturas();
        List<String[]> docentes = dao.obtenerDocentesDisponibles();
        req.setAttribute("listaAsignaturas", asignaturas);
        req.setAttribute("listaDocentes", docentes);
    }
}
