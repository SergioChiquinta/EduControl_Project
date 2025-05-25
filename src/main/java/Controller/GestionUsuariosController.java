
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
        
        String action = request.getParameter("action");
        String filtro = request.getParameter("filtro");
        String valor = request.getParameter("valor");
        
        try {
            if ("nuevo".equals(action)) {
                // Mostrar formulario para nuevo usuario
                request.getRequestDispatcher("nuevoUsuario.jsp").forward(request, response);
            } else if ("editar".equals(action)) {
                // Mostrar formulario para editar usuario
                int id = Integer.parseInt(request.getParameter("id"));
                Usuario u = usuarioDAO.obtenerUsuario(id);
                request.setAttribute("usuario", u);
                request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
            } else {
                // Listar usuarios con filtro si existe
                List<Usuario> usuarios = usuarioDAO.listarUsuarios(filtro, valor);
                request.setAttribute("usuarios", usuarios);
                request.setAttribute("filtro", filtro);
                request.setAttribute("valor", valor);
                request.getRequestDispatcher("gestion_usuarios.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar la solicitud");
            request.getRequestDispatcher("gestion_usuarios.jsp").forward(request, response);
        }
        
        // Verificar si es una solicitud AJAX
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));

        if (isAjax) {
            // Para solicitudes AJAX, solo enviar el contenido sin el layout completo
            request.getRequestDispatcher("gestion_usuarios.jsp").forward(request, response);
        } else {
            // Para solicitudes normales, enviar la página completa
            request.getRequestDispatcher("gestion_usuarios.jsp").forward(request, response);
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
                // Crear nuevo usuario
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
                // Actualizar usuario existente
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
                // Eliminar usuario
                int id = Integer.parseInt(request.getParameter("id"));
                
                if (usuarioDAO.eliminarUsuario(id)) {
                    request.setAttribute("exito", "Usuario eliminado exitosamente");
                } else {
                    request.setAttribute("error", "Error al eliminar usuario");
                }
            }
            
            // Redirigir a la lista de usuarios después de cualquier acción POST
            response.sendRedirect("GestionUsuariosController");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar la solicitud");
            request.getRequestDispatcher("gestion_usuarios.jsp").forward(request, response);
        }
    }
}