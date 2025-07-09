
package Dao;

import Config.clsConnection;
import Model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestionUsuarioDAO {

    public List<Usuario> obtenerTodosUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("rol")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Usuario> filtrarUsuarios(String campo, String valor) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE " + campo + " LIKE ?";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + valor + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            rs.getString("rol")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean crearUsuario(String nombre, String correo, String contrasena, String rol, String salonIdStr) {
        String sql = "INSERT INTO usuarios (nombre, correo, contraseña, rol) VALUES (?, ?, SHA2(?, 256), ?)";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setString(3, contrasena);
            ps.setString(4, rol);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int usuarioId = generatedKeys.getInt(1);

                        if ("estudiante".equals(rol) && salonIdStr != null && !salonIdStr.isEmpty()) {
                            int salonId = Integer.parseInt(salonIdStr);
                            insertarEstudiante(usuarioId, salonId);
                        }

                        if ("docente".equals(rol)) {
                            insertarDocente(usuarioId);
                        }

                        String accion = "Crear usuario";
                        String descripcion = "Se creó el usuario ID: " + usuarioId + ", nombre: " + nombre + ", rol: " + rol;
                        registrarActividad(usuarioId, accion, descripcion, "");
                    }
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void insertarEstudiante(int usuarioId, int salonId) throws SQLException {
        String sql = "INSERT INTO estudiantes (usuario_id, salon_id) VALUES (?, ?)";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, salonId);
            ps.executeUpdate();
        }
    }

    private void insertarDocente(int usuarioId) throws SQLException {
        String sql = "INSERT INTO docentes (usuario_id) VALUES (?)";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.executeUpdate();
        }
    }

    public boolean editarUsuario(int id, String nombre, String correo) {
        String sql = "UPDATE usuarios SET nombre = ?, correo = ? WHERE id = ?";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ps.setString(2, correo);
            ps.setInt(3, id);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                String accion = "Editar usuario";
                String descripcion = "Se editó el usuario ID: " + id + ", nuevo nombre: " + nombre + ", nuevo correo: " + correo;
                registrarActividad(id, accion, descripcion, "");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarUsuario(int id) {
        try (Connection conn = clsConnection.getConnection()) {
            conn.setAutoCommit(false);

            // No eliminar logs

            // Eliminar de estudiantes
            try (PreparedStatement psEst = conn.prepareStatement("DELETE FROM estudiantes WHERE usuario_id = ?")) {
                psEst.setInt(1, id);
                psEst.executeUpdate();
            }

            // Eliminar de docentes
            try (PreparedStatement psDoc = conn.prepareStatement("DELETE FROM docentes WHERE usuario_id = ?")) {
                psDoc.setInt(1, id);
                psDoc.executeUpdate();
            }

            // Registrar log ANTES de eliminar el usuario
            String accion = "Eliminar usuario";
            String descripcion = "Se eliminó el usuario ID: " + id;
            registrarActividad(id, accion, descripcion, "");

            // Finalmente usuarios
            try (PreparedStatement psUsu = conn.prepareStatement("DELETE FROM usuarios WHERE id = ?")) {
                psUsu.setInt(1, id);
                int rows = psUsu.executeUpdate();

                if (rows > 0) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String[]> obtenerSalones() {
        List<String[]> salones = new ArrayList<>();
        String sql = "SELECT id, nombre FROM salones";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                salones.add(new String[]{String.valueOf(rs.getInt("id")), rs.getString("nombre")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salones;
    }

    // Método privado para registrar logs
    private void registrarActividad(int usuarioId, String accion, String descripcion, String ip) {
        String sqlUsuario = "SELECT nombre, correo FROM usuarios WHERE id = ?";
        String nombre = "";
        String correo = "";

        try (Connection conn = clsConnection.getConnection();
             PreparedStatement psUser = conn.prepareStatement(sqlUsuario)) {

            psUser.setInt(1, usuarioId);
            try (ResultSet rs = psUser.executeQuery()) {
                if (rs.next()) {
                    nombre = rs.getString("nombre");
                    correo = rs.getString("correo");
                }
            }

            String sql = "CALL registrar_log(?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, usuarioId);
                ps.setString(2, nombre);         // usuario_nombre
                ps.setString(3, correo);         // usuario_correo
                ps.setString(4, accion);
                ps.setString(5, descripcion);
                ps.setString(6, ip);
                ps.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
