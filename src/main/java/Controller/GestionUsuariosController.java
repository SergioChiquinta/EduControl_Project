
package Controller;

import Dao.GestionUsuarioDAO;
import Model.Usuario;
import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@MultipartConfig
@WebServlet("/GestionUsuariosController")
public class GestionUsuariosController extends HttpServlet {

    private GestionUsuarioDAO dao = new GestionUsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filtroCampo = req.getParameter("campo");
        String filtroValor = req.getParameter("valor");

        List<Usuario> usuarios;

        // ✅ Si no hay filtro, siempre traer todos los usuarios
        if (filtroCampo == null || filtroValor == null || filtroCampo.isEmpty() || filtroValor.isEmpty()) {
            usuarios = dao.obtenerTodosUsuarios();
        } else {
            usuarios = dao.filtrarUsuarios(filtroCampo, filtroValor);
        }

        req.setAttribute("listaUsuarios", usuarios);
        req.setAttribute("listaSalones", dao.obtenerSalones());

        // ✅ Para AJAX, devolvemos solo fragmento si es necesario
        String isAjax = req.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(isAjax)) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("gestionUsuariosAdmin.jsp");
            dispatcher.forward(req, resp);
        } else {
            // Si no es AJAX, recargar igual
            RequestDispatcher dispatcher = req.getRequestDispatcher("gestionUsuariosAdmin.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        if ("crear".equals(accion)) {
            String nombre = req.getParameter("nombre");
            String correo = req.getParameter("correo");
            String contrasena = req.getParameter("contrasena");
            String rol = req.getParameter("rol");
            String salonIdStr = req.getParameter("salonId");

            // ✅ Validar correo simple
            if (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                req.setAttribute("mensaje", "Formato de correo inválido.");
                doGet(req, resp);
                return;
            }

            boolean creado = dao.crearUsuario(nombre, correo, contrasena, rol, salonIdStr);

            if (creado) {
                req.setAttribute("mensaje", "Usuario creado exitosamente.");
            } else {
                req.setAttribute("mensaje", "Error al crear el usuario. Verifica los datos.");
            }
        }

        if ("editar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String nombre = req.getParameter("nombre");
            String correo = req.getParameter("correo");

            if (!correo.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                req.setAttribute("mensaje", "Formato de correo inválido.");
                doGet(req, resp);
                return;
            }

            boolean actualizado = dao.editarUsuario(id, nombre, correo);

            if (actualizado) {
                req.setAttribute("mensaje", "Usuario actualizado exitosamente.");
            } else {
                req.setAttribute("mensaje", "Error al actualizar usuario.");
            }
        }

        if ("eliminar".equals(accion)) {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean eliminado = dao.eliminarUsuario(id);

            if (eliminado) {
                req.setAttribute("mensaje", "Usuario eliminado correctamente.");
            } else {
                req.setAttribute("mensaje", "Error al eliminar usuario.");
            }
        }

        doGet(req, resp);
    }
}
