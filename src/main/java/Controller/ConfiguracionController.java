
package Controller;

import Dao.ConfiguracionUsuarioDAO;
import Model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@MultipartConfig
@WebServlet("/ConfiguracionController")
public class ConfiguracionController extends HttpServlet {

    private ConfiguracionUsuarioDAO dao = new ConfiguracionUsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        Usuario datosActualizados = dao.obtenerUsuarioPorId(usuario.getId());
        req.setAttribute("usuario", datosActualizados);

        RequestDispatcher dispatcher = req.getRequestDispatcher("configuracion.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nombre = req.getParameter("nombre");
        String correo = req.getParameter("correo");
        String nuevaContrasena = req.getParameter("contrasena");

        HttpSession session = req.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        usuario.setUsername(nombre);
        usuario.setCorreo(correo);

        boolean actualizado = dao.actualizarUsuario(usuario, nuevaContrasena);

        if (actualizado) {
            session.setAttribute("usuario", usuario);
            req.setAttribute("mensaje", "Datos actualizados correctamente.");
        } else {
            req.setAttribute("mensaje", "Error al actualizar los datos.");
        }

        Usuario datosActualizados = dao.obtenerUsuarioPorId(usuario.getId());
        req.setAttribute("usuario", datosActualizados);

        RequestDispatcher dispatcher = req.getRequestDispatcher("configuracion.jsp");
        dispatcher.forward(req, resp);
    }
}
