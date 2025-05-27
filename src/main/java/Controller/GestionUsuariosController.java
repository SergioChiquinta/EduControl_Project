package Controller;

import Dao.UsuarioDAO;
import Model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/GestionUsuariosController")
public class GestionUsuariosController extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Verificar autenticación y rol de administrador
        if (usuario == null || !"administrador".equals(usuario.getRol())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String filtro = request.getParameter("filtro");
        String valor = request.getParameter("valor");

        if ("Todos".equals(filtro)) {
            filtro = null;
            valor = null;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Verificar autenticación y rol de administrador
        if (usuario == null || !"administrador".equals(usuario.getRol())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("crear".equals(action)) {
                Usuario nuevo = new Usuario();
                nuevo.setUsername(request.getParameter("nombre"));
                nuevo.setCorreo(request.getParameter("correo"));
                nuevo.setRol(request.getParameter("rol"));
                String password = request.getParameter("password");

                if (usuarioDAO.existeCorreo(nuevo.getCorreo())) {
                    request.setAttribute("error", "El correo ya está registrado");
                    request.setAttribute("usuario", nuevo);
                    request.getRequestDispatcher("nuevoUsuario.jsp").forward(request, response);
                    return;
                }

                if (usuarioDAO.crearUsuario(nuevo, password)) {
                    request.setAttribute("exito", "Usuario creado exitosamente");
                } else {
                    request.setAttribute("error", "Error al crear usuario");
                }

            } else if ("actualizar".equals(action)) {
                Usuario actualizado = new Usuario();
                actualizado.setId(Integer.parseInt(request.getParameter("id")));
                actualizado.setUsername(request.getParameter("nombre"));
                actualizado.setCorreo(request.getParameter("correo"));
                actualizado.setRol(request.getParameter("rol"));

                if (usuarioDAO.actualizarUsuario(actualizado)) {
                    request.setAttribute("exito", "Usuario actualizado exitosamente");
                } else {
                    request.setAttribute("error", "Error al actualizar usuario");
                }

            } else if ("eliminar".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));

                if (usuarioDAO.eliminarUsuario(id)) {
                    request.setAttribute("exito", "Usuario eliminado exitosamente");
                } else {
                    request.setAttribute("error", "Error al eliminar usuario");
                }
            }

            // Redirigir a la lista de usuarios
            response.sendRedirect("GestionUsuariosController");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar la solicitud");
            request.getRequestDispatcher("gestion_usuarios.jsp").forward(request, response);
        }
    }
}