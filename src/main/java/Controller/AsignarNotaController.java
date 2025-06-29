
package Controller;

import Dao.NotaDAO;
import Dao.UsuarioDAO;
import Model.Evaluacion;
import Model.EstudianteNotaDTO;
import Model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@MultipartConfig
@WebServlet("/AsignarNotaController")
public class AsignarNotaController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private NotaDAO notaDao;
    private UsuarioDAO usuarioDao;

    public AsignarNotaController() throws SQLException {
        super();
        this.notaDao = new NotaDAO();
        this.usuarioDao = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setCharacterEncoding("UTF-8");
        Usuario usuarioLogeado = null;
        int docenteId = -1;

        if (session != null) {
            usuarioLogeado = (Usuario) session.getAttribute("usuario");
        }

        // Verificar si el usuario está logeado y es docente
        if (usuarioLogeado == null || !"docente".equals(usuarioLogeado.getRol())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Obtener el ID del docente
        if (usuarioLogeado != null) {
            Integer foundDocenteId = usuarioDao.obtenerIdDocentePorIdUsuario(usuarioLogeado.getId());
            if (foundDocenteId != null) {
                docenteId = foundDocenteId;
                session.setAttribute("docente_id", docenteId);
            }
        }

        if (docenteId == -1) {
            request.setAttribute("error", "No se encontró un perfil de docente asociado a su cuenta.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            // Obtener salones y evaluaciones para el docente
            List<String> salones = notaDao.obtenerSalonesPorDocente(docenteId);
            List<Evaluacion> evaluaciones = notaDao.obtenerEvaluacionesPorDocente(docenteId);

            request.setAttribute("salones", salones);
            request.setAttribute("evaluaciones", evaluaciones);

            // Si hay parámetros de filtro, obtener estudiantes con notas
            String salonSeleccionado = request.getParameter("salon");
            String nombreSalon = null;
            String nombrePeriodo = null;

            String evaluacionSeleccionada = request.getParameter("evaluacion");

// Solo dividir si NO es nulo y no vacío
            if (salonSeleccionado != null && !salonSeleccionado.isEmpty()
                    && evaluacionSeleccionada != null && !evaluacionSeleccionada.isEmpty()) {

                String[] partes = salonSeleccionado.split(" - ");
                nombreSalon = partes[0];
                nombrePeriodo = partes.length > 1 ? partes[1] : "";

                int evalId = Integer.parseInt(evaluacionSeleccionada);
                List<EstudianteNotaDTO> estudiantes = notaDao.obtenerEstudiantesConNotas(nombreSalon, nombrePeriodo, evalId);

                request.setAttribute("estudiantes", estudiantes);
                request.setAttribute("salonSeleccionado", salonSeleccionado);
                request.setAttribute("evaluacionSeleccionada", evalId);
            }

            if (salonSeleccionado != null && !salonSeleccionado.isEmpty()
                    && evaluacionSeleccionada != null && !evaluacionSeleccionada.isEmpty()) {

                int evalId = Integer.parseInt(evaluacionSeleccionada);
                List<EstudianteNotaDTO> estudiantes = notaDao.obtenerEstudiantesConNotas(nombreSalon, nombrePeriodo, evalId);

                request.setAttribute("estudiantes", estudiantes);
                request.setAttribute("salonSeleccionado", salonSeleccionado);
                request.setAttribute("evaluacionSeleccionada", evalId);
            }

            boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

            if (isAjax) {
                request.getRequestDispatcher("notasDocente.jsp").forward(request, response);
            } else {
                // Redirección tradicional si accede directo
                response.sendRedirect("docenteDashboard.jsp?page=notasDocente.jsp");
            }

        } catch (SQLException e) {
            throw new ServletException("Error al acceder a la base de datos", e);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Formato de evaluación inválido");
            request.getRequestDispatcher("notasDocente.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Usuario usuarioLogeado = (Usuario) session.getAttribute("usuario");

        if (usuarioLogeado == null || !"docente".equals(usuarioLogeado.getRol())) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            String[] notasStr = request.getParameterValues("nota[]");
            String[] estudiantesStr = request.getParameterValues("estudianteId[]");
            String evaluacionStr = request.getParameter("evaluacionSeleccionada");
            String salonSeleccionado = request.getParameter("salonSeleccionado");

            if (notasStr == null || estudiantesStr == null || evaluacionStr == null) {
                request.setAttribute("error", "Faltan parámetros del formulario.");
                request.getRequestDispatcher("notasDocente.jsp").forward(request, response);
                return;
            }

            int evaluacionId = Integer.parseInt(evaluacionStr);

            for (int i = 0; i < estudiantesStr.length; i++) {
                if (notasStr[i] != null && !notasStr[i].trim().isEmpty()) {
                    int estudianteId = Integer.parseInt(estudiantesStr[i]);
                    double nota = Double.parseDouble(notasStr[i].replace(",", ".")); // por si usas coma decimal
                    notaDao.guardarNota(estudianteId, evaluacionId, nota);
                }
            }

            // Guardar los filtros en sesión
            session.setAttribute("ultimoSalonFiltrado", salonSeleccionado);
            session.setAttribute("ultimaEvaluacionFiltrada", evaluacionId);

            // Redirigir manteniendo los filtros
            String redirectUrl = "AsignarNotaController?salon="
                    + java.net.URLEncoder.encode(salonSeleccionado, "UTF-8")
                    + "&evaluacion=" + evaluacionId;

            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.sendRedirect(redirectUrl);
            } else {
                response.sendRedirect(redirectUrl);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Formato de nota o ID inválido.");
            request.getRequestDispatcher("notasDocente.jsp").forward(request, response);
            e.printStackTrace();
            response.setStatus(400);
            response.setContentType("text/plain");
            response.getWriter().write("Error de formato en nota o ID.");
        } catch (SQLException e) {
            e.printStackTrace(); // para ver el detalle en consola
            response.setStatus(500);
            response.setContentType("text/plain");
            response.getWriter().write("Error SQL: " + e.getMessage());
        }

    }

}
