
package Controller;

import Dao.UsuarioDAO;
import Model.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String identificador = request.getParameter("identificador");
        String clave = request.getParameter("clave");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario u = usuarioDAO.validarUsuario(identificador, clave);

        if (u != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", u);

            switch (u.getRol()) {
                case "administrador":
                    response.sendRedirect("adminDashboard.jsp");
                    break;
                case "docente":
                    response.sendRedirect("docenteDashboard.jsp");
                    break;
                case "estudiante":
                    response.sendRedirect("estudianteDashboard.jsp");
                    break;
                default:
                    response.sendRedirect("login.jsp");
                    break;
            }
        } else {
            request.setAttribute("error", "Correo o contraseña inválidos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
