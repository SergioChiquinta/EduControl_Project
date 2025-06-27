
package Controller;

import Dao.DatosAcademicosDAO;
import Dao.ReportesDAO;
import Model.Reporte;
import Util.PDFGenerator;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@MultipartConfig
@WebServlet("/ReporteController")
public class ReporteController extends HttpServlet {

    private ReportesDAO dao = new ReportesDAO();
    private DatosAcademicosDAO datosDAO = new DatosAcademicosDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        if ("verPDF".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            byte[] pdf = dao.obtenerPDF(id);
            if (pdf != null) {
                resp.setContentType("application/pdf");
                resp.setHeader("Content-Disposition", "inline; filename=reporte.pdf");
                OutputStream os = resp.getOutputStream();
                os.write(pdf);
                os.close();
            }
        } else if ("descargarPDF".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            byte[] pdf = dao.obtenerPDF(id);
            if (pdf != null) {
                resp.setContentType("application/octet-stream");
                resp.setHeader("Content-Disposition", "attachment; filename=reporte.pdf");
                OutputStream os = resp.getOutputStream();
                os.write(pdf);
                os.close();
            }
        } else {

            // Cargar reportes del usuario
            HttpSession session = req.getSession();
            System.out.println("Usuario en sesión: " + session.getAttribute("usuarioId"));
            Model.Usuario usuario = (Model.Usuario) req.getSession().getAttribute("usuario");
            if (usuario == null) {
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sesión expirada");
                return;
            }
            int usuarioId = usuario.getId(); // 👈 ahora sí tienes el ID correcto

            List<Reporte> reportes = dao.obtenerReportesPorUsuario(usuarioId);
            req.setAttribute("listaReportes", reportes);
            RequestDispatcher dispatcher = req.getRequestDispatcher("reportes.jsp");
            dispatcher.forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        if ("generar".equals(accion)) {
            HttpSession session = req.getSession();
            if (Boolean.TRUE.equals(session.getAttribute("generandoReporte"))) {
                System.out.println("⛔ Se intentó generar un reporte mientras otro estaba en proceso.");
                resp.sendError(HttpServletResponse.SC_CONFLICT, "Ya se está generando un reporte.");
                return;
            }
            session.setAttribute("generandoReporte", true); // ⚠️ Bloqueo temporal
            try {
                int estudianteId = Integer.parseInt(req.getParameter("estudianteId"));
                int salonId = Integer.parseInt(req.getParameter("salonId"));
                int periodoId = Integer.parseInt(req.getParameter("periodoId"));
                Model.Usuario usuario = (Model.Usuario) req.getSession().getAttribute("usuario");
                if (usuario == null) {
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Sesión expirada");
                    return;
                }
                int usuarioId = usuario.getId(); // Ahora sí tienes el ID del usuario autenticado

                // Aquí iría la lógica para calcular promedio y estado
                double promedio = datosDAO.calcularPromedioEstudiante(estudianteId, periodoId);
                String estado = promedio >= 11 ? "Aprobado" : "Desaprobado";

                String nombreEstudiante = datosDAO.obtenerNombre("usuarios_estudiante", estudianteId);
                String salon = datosDAO.obtenerNombre("salones", salonId);
                String periodo = datosDAO.obtenerNombre("periodos_academicos", periodoId);

                Map<String, Double> promediosMateria = datosDAO.calcularPromediosPorMateria(estudianteId, periodoId);
                double promedioGeneral = promediosMateria.values().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

                byte[] pdf = PDFGenerator.generarPDFReporte(nombreEstudiante, salon, periodo, promediosMateria, promedioGeneral, estado);

                Reporte r = new Reporte();
                r.setEstudianteId(estudianteId);
                r.setSalonId(salonId);
                r.setPeriodoId(periodoId);
                r.setUsuarioId(usuarioId);
                r.setPromedioGeneral(promedio);
                r.setEstadoAcademico(estado);
                r.setPdf(pdf);

                dao.insertarReporte(r);

                if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
                    List<Reporte> reportes = dao.obtenerReportesPorUsuario(usuarioId);
                    req.setAttribute("listaReportes", reportes);
                    RequestDispatcher dispatcher = req.getRequestDispatcher("reportes.jsp");
                    dispatcher.forward(req, resp);
                    return;
                } else {
                    doGet(req, resp);
                }

            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno al generar el reporte.");
            } finally {
                req.getSession().removeAttribute("generandoReporte");
            }

        }
    }
}
