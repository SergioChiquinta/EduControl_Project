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

@WebServlet(name = "ConfiguracionController", urlPatterns = {"/ConfiguracionController"})
public class ConfiguracionController extends HttpServlet { // Corregido: Eliminada la palabra clave 'class' duplicada

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Handles the HTTP GET method.
     * Muestra la página de configuración.
     * Asegura que el usuario esté logueado y pasa el objeto Usuario a la sesión.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        System.out.println("ConfiguracionController: doGet - Usuario en sesión: " + (usuarioLogueado != null ? usuarioLogueado.getUsername() : "NULO"));

        if (usuarioLogueado == null) {
            System.out.println("ConfiguracionController: doGet - Sesión inválida. Enviando 401 Unauthorized.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.getWriter().write("Sesión inválida. Por favor, inicia sesión de nuevo.");
            return;
        }

        request.getRequestDispatcher("configuracion.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP POST method.
     * Procesa la actualización de los datos de configuración del usuario.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        System.out.println("ConfiguracionController: doPost - Inicio. Usuario en sesión: " + (usuarioLogueado != null ? usuarioLogueado.getUsername() : "NULO"));

        // Declarar mensajeExito y mensajeError al inicio del método
        String mensajeExito = null;
        String mensajeError = null;
        boolean cambiosRealizados = false;

        if (usuarioLogueado == null) {
            System.out.println("ConfiguracionController: doPost - Sesión inválida. Enviando 401 Unauthorized.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.getWriter().write("Sesión inválida. Por favor, inicia sesión de nuevo.");
            return;
        }

        // 1. Obtener parámetros del formulario
        int idUsuario = -1; // Valor por defecto para indicar que no se pudo parsear
        try {
            String idUsuarioStr = request.getParameter("idUsuario");
            if (idUsuarioStr != null && !idUsuarioStr.isEmpty()) {
                idUsuario = Integer.parseInt(idUsuarioStr);
                System.out.println("ConfiguracionController: doPost - idUsuario del formulario: " + idUsuario);
            } else {
                mensajeError = "ID de usuario no proporcionado.";
                System.err.println("ConfiguracionController: doPost - Error: idUsuario es nulo o vacío.");
                request.setAttribute("mensajeError", mensajeError);
                request.getRequestDispatcher("configuracion.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("ConfiguracionController: doPost - Error al parsear idUsuario: " + request.getParameter("idUsuario") + " - " + e.getMessage());
            mensajeError = "ID de usuario inválido.";
            request.setAttribute("mensajeError", mensajeError);
            request.getRequestDispatcher("configuracion.jsp").forward(request, response);
            return; // Detener ejecución si el ID es inválido
        }
        
        String nuevoUsername = request.getParameter("nombre");
        String nuevoCorreo = request.getParameter("email");
        String nuevaPassword = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        System.out.println("ConfiguracionController: doPost - Parámetros recibidos:");
        System.out.println("  Nombre: " + nuevoUsername);
        System.out.println("  Correo: " + nuevoCorreo);
        System.out.println("  Nueva Contraseña (longitud): " + (nuevaPassword != null ? nuevaPassword.length() : "NULO"));
        System.out.println("  Confirmar Contraseña (longitud): " + (confirmPassword != null ? confirmPassword.length() : "NULO"));

        try {
            // 2. Validaciones básicas
            if (nuevoUsername == null || nuevoUsername.trim().isEmpty()) {
                mensajeError = "El nombre de usuario no puede estar vacío.";
            } else if (nuevoCorreo == null || nuevoCorreo.trim().isEmpty()) {
                mensajeError = "El correo electrónico no puede estar vacío.";
            } else if (!isValidEmail(nuevoCorreo)) {
                mensajeError = "Formato de correo electrónico inválido.";
            } else {
                if (!usuarioLogueado.getCorreo().equalsIgnoreCase(nuevoCorreo) && usuarioDAO.existeCorreo(nuevoCorreo)) {
                    mensajeError = "El correo electrónico ya está registrado por otro usuario.";
                }
            }
            System.out.println("ConfiguracionController: doPost - Mensaje de error inicial (validación de campos): " + mensajeError);

            if (mensajeError == null) { // Si no hay errores de validación iniciales

                // 4. Actualizar username y correo si han cambiado
                boolean usernameCambiado = !usuarioLogueado.getUsername().equals(nuevoUsername);
                boolean correoCambiado = !usuarioLogueado.getCorreo().equals(nuevoCorreo);

                System.out.println("ConfiguracionController: doPost - Username cambiado: " + usernameCambiado + ", Correo cambiado: " + correoCambiado);

                if (usernameCambiado || correoCambiado) {
                    Usuario usuarioParaActualizar = new Usuario();
                    usuarioParaActualizar.setId(idUsuario);
                    usuarioParaActualizar.setUsername(nuevoUsername);
                    usuarioParaActualizar.setCorreo(nuevoCorreo);
                    usuarioParaActualizar.setRol(usuarioLogueado.getRol()); // Mantener el rol actual

                    System.out.println("ConfiguracionController: doPost - Intentando actualizar nombre/correo con DAO...");
                    if (usuarioDAO.actualizarUsuario(usuarioParaActualizar)) {
                        mensajeExito = "Información de perfil actualizada correctamente.";
                        cambiosRealizados = true;
                        System.out.println("ConfiguracionController: doPost - Nombre/correo actualizados con éxito.");
                    } else {
                        mensajeError = "No se pudo actualizar la información de perfil.";
                        System.out.println("ConfiguracionController: doPost - Fallo al actualizar nombre/correo.");
                    }
                } else {
                    System.out.println("ConfiguracionController: doPost - Nombre y correo no cambiaron.");
                }

                // 5. Actualizar contraseña si se proporcionó una nueva
                if (nuevaPassword != null && !nuevaPassword.trim().isEmpty()) {
                    System.out.println("ConfiguracionController: doPost - Se detectó nueva contraseña.");
                    if (!nuevaPassword.equals(confirmPassword)) {
                        mensajeError = "Las nuevas contraseñas no coinciden.";
                        System.out.println("ConfiguracionController: doPost - Error: Contraseñas no coinciden.");
                    } else if (nuevaPassword.length() < 6) {
                        mensajeError = "La contraseña debe tener al menos 6 caracteres.";
                        System.out.println("ConfiguracionController: doPost - Error: Contraseña demasiado corta.");
                    } else {
                        System.out.println("ConfiguracionController: doPost - Intentando actualizar contraseña con DAO...");
                        if (usuarioDAO.actualizarPassword(idUsuario, nuevaPassword)) {
                            if (mensajeExito == null) {
                                mensajeExito = "Contraseña actualizada correctamente.";
                            } else {
                                mensajeExito += " Contraseña actualizada correctamente.";
                            }
                            cambiosRealizados = true;
                            System.out.println("ConfiguracionController: doPost - Contraseña actualizada con éxito.");
                        } else {
                            if (mensajeError == null) {
                                mensajeError = "No se pudo actualizar la contraseña.";
                            } else {
                                mensajeError += " No se pudo actualizar la contraseña.";
                            }
                            System.out.println("ConfiguracionController: doPost - Fallo al actualizar contraseña.");
                        }
                    }
                } else {
                    System.out.println("ConfiguracionController: doPost - No se proporcionó nueva contraseña.");
                }
            }

            // 6. Actualizar el objeto Usuario en la sesión si hubo cambios exitosos
            System.out.println("ConfiguracionController: doPost - Cambios realizados: " + cambiosRealizados + ", Mensaje de error final: " + mensajeError);
            if (cambiosRealizados && mensajeError == null) {
                System.out.println("ConfiguracionController: doPost - Recargando usuario de la DB para actualizar sesión...");
                Usuario usuarioActualizado = usuarioDAO.obtenerUsuarioPorId(idUsuario);
                if (usuarioActualizado != null) {
                    session.setAttribute("usuarioLogueado", usuarioActualizado);
                    System.out.println("ConfiguracionController: doPost - Sesión de usuario actualizada con éxito: " + usuarioActualizado.getUsername());
                } else {
                    mensajeError = "Error al recargar los datos del usuario en la sesión.";
                    System.err.println("ConfiguracionController: doPost - Fallo al recargar usuario actualizado de la DB.");
                }
            } else if (!cambiosRealizados && mensajeError == null) {
                mensajeExito = "No se realizaron cambios.";
                System.out.println("ConfiguracionController: doPost - No se realizaron cambios en los datos.");
            }

        } catch (Exception e) {
            mensajeError = "Ocurrió un error inesperado al procesar la solicitud.";
            System.err.println("ConfiguracionController: doPost - Excepción inesperada: " + e.getMessage());
            e.printStackTrace();
        }

        // 7. Reenviar a la página de configuración con mensajes
        request.setAttribute("mensajeExito", mensajeExito);
        request.setAttribute("mensajeError", mensajeError);
        System.out.println("ConfiguracionController: doPost - Reenviando a configuracion.jsp. Mensaje Exito: '" + mensajeExito + "', Mensaje Error: '" + mensajeError + "'");
        request.getRequestDispatcher("configuracion.jsp").forward(request, response);
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
}
