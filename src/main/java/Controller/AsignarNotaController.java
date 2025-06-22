
package Controller;

import Dao.NotaDAO;
import Dao.UsuarioDAO;
import Model.Evaluacion;
import Model.EstudianteNotaDTO;
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
            String evaluacionSeleccionada = request.getParameter("evaluacion");
            
            if (salonSeleccionado != null && !salonSeleccionado.isEmpty() && 
                evaluacionSeleccionada != null && !evaluacionSeleccionada.isEmpty()) {
                
                int evalId = Integer.parseInt(evaluacionSeleccionada);
                List<EstudianteNotaDTO> estudiantes = notaDao.obtenerEstudiantesConNotas(salonSeleccionado, evalId);
                
                request.setAttribute("estudiantes", estudiantes);
                request.setAttribute("salonSeleccionado", salonSeleccionado);
                request.setAttribute("evaluacionSeleccionada", evalId);
            }
            
            request.getRequestDispatcher("notasDocente.jsp").forward(request, response);
            
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

            // Redirigir con parámetros codificados
            response.sendRedirect("AsignarNotaController?salon=" + 
                java.net.URLEncoder.encode(salonSeleccionado, "UTF-8") +
                "&evaluacion=" + evaluacionId);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Formato de nota o ID inválido.");
            request.getRequestDispatcher("notasDocente.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error al procesar la nota en la base de datos", e);
        }
    }

}