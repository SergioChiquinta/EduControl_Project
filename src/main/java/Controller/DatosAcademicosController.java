
package Controller;

import Dao.DatosAcademicosDAO;
import com.google.gson.Gson;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/DatosAcademicosController")
public class DatosAcademicosController extends HttpServlet {

    private final DatosAcademicosDAO dao = new DatosAcademicosDAO();
    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        switch (accion) {
            case "periodos":
                out.print(gson.toJson(dao.obtenerPeriodos()));
                break;

            case "salones":
                int periodoId = Integer.parseInt(req.getParameter("periodoId"));
                out.print(gson.toJson(dao.obtenerSalonesPorPeriodo(periodoId)));
                break;

            case "estudiantes":
                int salonId = Integer.parseInt(req.getParameter("salonId"));
                out.print(gson.toJson(dao.obtenerEstudiantesPorSalon(salonId)));
                break;

            default:
                out.print("[]");
                break;
        }

    }
}
