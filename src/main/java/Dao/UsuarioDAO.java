
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
    public List<Usuario> listarUsuarios(String filtro, String valor) {
        List<Usuario> usuarios = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = conexion.getConnection();
            StringBuilder sql = new StringBuilder("SELECT id, nombre, correo, rol FROM usuarios");

            if (filtro != null && !filtro.isEmpty() && valor != null && !valor.isEmpty()) {
                sql.append(" WHERE ").append(filtro).append(" LIKE ?");
            }

            sql.append(" ORDER BY nombre");

            stmt = conn.prepareStatement(sql.toString());

            if (filtro != null && !filtro.isEmpty() && valor != null && !valor.isEmpty()) {
                stmt.setString(1, "%" + valor + "%");
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                usuarios.add(new Usuario(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("correo"),
                    rs.getString("rol")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error en listarUsuarios: " + e.getMessage());
            throw new RuntimeException("Error al listar usuarios", e);
        } finally {
            // Cerrar recursos
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* ignorar */ }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { /* ignorar */ }
            try { if (conn != null) conn.close(); } catch (SQLException e) { /* ignorar */ }
        }

        return usuarios;
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

    // Crear usuario nuevo
    public boolean crearUsuario(Usuario usuario, String password) {
        String sql = "INSERT INTO usuarios (nombre, correo, rol, contraseña) VALUES (?, ?, ?, SHA2(?, 256))";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getRol());
            stmt.setString(4, password);

            int filas = stmt.executeUpdate();
            
            if (filas > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Actualizar usuario
    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre=?, correo=?, rol=? WHERE id=?";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getCorreo());
            stmt.setString(3, usuario.getRol());
            stmt.setInt(4, usuario.getId());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    // Eliminar usuario por ID
    public boolean eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int filas = stmt.executeUpdate();
            return filas > 0;

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
}