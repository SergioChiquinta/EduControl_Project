
package Dao;

import Config.clsConnection;
import Model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private final clsConnection conexion = new clsConnection();

    // Validar usuario para login
    public Usuario validarUsuario(String identificador, String clave) {
        Usuario usuario = null;
        String sql = "SELECT id, nombre, correo, rol FROM usuarios " +
                     "WHERE (correo = ? OR nombre = ?) AND contraseña = SHA2(?, 256)";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, identificador);
            stmt.setString(2, identificador);
            stmt.setString(3, clave);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("rol")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }

    // Listar usuarios con filtro
    public List<Usuario> listarUsuariosFiltrado(String filtro, String valor) {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "{CALL sp_listar_usuarios_filtrado(?, ?)}";

        try (Connection conn = conexion.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, filtro);
            stmt.setString(2, valor);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                usuarios.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("rol")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error en listarUsuariosFiltrado: " + e.getMessage());
        }

        return usuarios;
    }
    
    // Listar todos los usuarios si no hay filtros
    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nombre, correo, rol FROM usuarios";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                usuarios.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("rol")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    // Crear usuario nuevo
    public boolean crearUsuario(Usuario usuario, String password) {
        String sql = "{CALL sp_crear_usuario(?, ?, ?, ?)}";

        try (Connection conn = conexion.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, usuario.getUsername()); // nombre
            stmt.setString(2, usuario.getCorreo());   // correo
            stmt.setString(3, password);              // contraseña sin encriptar, se cifra en SP
            stmt.setString(4, usuario.getRol());      // rol (docente, estudiante, administrador)

            stmt.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Actualizar usuario
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "{CALL sp_actualizar_usuario(?, ?, ?, ?)}";

        try (Connection conn = conexion.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getUsername());
            stmt.setString(3, usuario.getCorreo());
            stmt.setString(4, usuario.getRol());

            stmt.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar usuario por ID
    public boolean eliminarUsuario(int id) {
        String sql = "{CALL sp_eliminar_usuario(?)}";

        try (Connection conn = conexion.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, id);
            stmt.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Verificar si correo existe
    public boolean existeCorreo(String correo) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    // Obtener usuario por ID
    public Usuario obtenerUsuario(int id) {
        Usuario usuario = null;
        String sql = "SELECT id, nombre, correo, rol FROM usuarios WHERE id = ?";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("rol")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }
    
    // Actualizar contraseña
    public boolean actualizarPassword(int id, String nuevaPassword) {
        String sql = "UPDATE usuarios SET contraseña = SHA2(?, 256) WHERE id = ?";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevaPassword);
            stmt.setInt(2, id);

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    //METODO DE OBTENER LA ID DEL DOCENTE POR LA ID DEL USUARIO
    
     public Integer obtenerIdDocentePorIdUsuario(int idUsuario) {
        Integer docenteId = null;
        // La consulta busca el 'id' de la tabla 'docentes'
        // donde 'docentes.usuario_id' coincide con el 'idUsuario' proporcionado.
        String sql = "SELECT id FROM docentes WHERE usuario_id = ?";
        try (Connection con = clsConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    docenteId = rs.getInt("id"); // Obtiene el ID de la tabla 'docentes'
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el ID del docente por ID de usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return docenteId;
    }
}