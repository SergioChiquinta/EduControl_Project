
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
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario u = usuarioDAO.validarUsuario(usuario, clave);

        if (u != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", u);
            response.sendRedirect("dashboard.jsp");
        } else {
            request.setAttribute("error", "Usuario o contraseña inválida.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
