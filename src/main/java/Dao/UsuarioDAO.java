
package Dao;

import Config.clsConnection;
import Model.Usuario;
import java.sql.*;

public class UsuarioDAO {

    private final clsConnection conexion = new clsConnection();

    // Validar usuario para login
    public Usuario validarUsuario(String identificador, String clave) {
        Usuario usuario = null;
        String sql = "SELECT id, nombre, correo, rol FROM usuarios "
                + "WHERE (correo = ? OR nombre = ?) AND contraseña = SHA2(?, 256)";

        try (Connection conn = conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

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

    //METODO DE OBTENER LA ID DEL DOCENTE POR LA ID DEL USUARIO
    public Integer obtenerIdDocentePorIdUsuario(int idUsuario) {
        Integer docenteId = null;
        // La consulta busca el 'id' de la tabla 'docentes'
        // donde 'docentes.usuario_id' coincide con el 'idUsuario' proporcionado.
        String sql = "SELECT id FROM docentes WHERE usuario_id = ?";
        try (Connection con = clsConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
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
