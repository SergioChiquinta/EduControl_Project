
package Controller;

import Dao.AsignaturaDAO;
import Model.Asignatura;
import Dao.UsuarioDAO;
import Model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@WebServlet("/AsignaturasDocenteController")
public class AsignaturasDocenteController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AsignaturaDAO asignaturaDao;
    private UsuarioDAO usuarioDao; 
    public AsignaturasDocenteController() {
        super();
        this.asignaturaDao = new AsignaturaDAO();
       this.usuarioDao = new UsuarioDAO(); 

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false); 

        Usuario usuarioLogeado = null;
        int docenteId = -1; // Valor por defecto

        if (session != null) {
            usuarioLogeado = (Usuario) session.getAttribute("usuario"); // Obtener el objeto Usuario
        }

        // Verificar si el usuario está logeado y tiene el rol correcto
        if (usuarioLogeado == null || !"docente".equals(usuarioLogeado.getRol())) {
            // Si no está logeado o no es un docente, redirigir a login
            response.sendRedirect("login.jsp");
            return;
        }

        // Si es un docente, obtener su ID de usuario y buscar el ID de docente asociado
        if (usuarioLogeado != null) {
            Integer foundDocenteId = usuarioDao.obtenerIdDocentePorIdUsuario(usuarioLogeado.getId());
            if (foundDocenteId != null) {
                docenteId = foundDocenteId;
            }
        }

        // Si el docenteId aún es -1 (no se encontró un perfil de docente asociado), redirigir
        if (docenteId == -1) {
            // Podrías añadir un mensaje de error a la request si lo deseas
            request.setAttribute("error", "No se encontró un perfil de docente asociado a su cuenta.");
            request.getRequestDispatcher("login.jsp").forward(request, response); // O a una página de error
            return;
        }

        // Ahora usas el docenteId correctamente obtenido del perfil del docente logeado
        List<Asignatura> asignaturas = asignaturaDao.obtenerAsignaturasPorDocente(docenteId);
        request.setAttribute("asignaturas", asignaturas);
        request.getRequestDispatcher("asignaturasDocente.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
