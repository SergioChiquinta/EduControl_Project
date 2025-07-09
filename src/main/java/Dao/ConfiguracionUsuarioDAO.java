
package Dao;

import Config.clsConnection;
import Model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConfiguracionUsuarioDAO {

    public Usuario obtenerUsuarioPorId(int id) {
        String sql = "SELECT id, nombre, correo, rol FROM usuarios WHERE id = ?";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setUsername(rs.getString("nombre"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setRol(rs.getString("rol"));
                return usuario;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean actualizarUsuario(Usuario usuario, String nuevaContrasena) {
        String sqlSinPass = "UPDATE usuarios SET nombre = ?, correo = ? WHERE id = ?";
        String sqlConPass = "UPDATE usuarios SET nombre = ?, correo = ?, contraseña = SHA2(?, 256) WHERE id = ?";

        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 (nuevaContrasena != null && !nuevaContrasena.isEmpty()) ? sqlConPass : sqlSinPass)) {

            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getCorreo());

            if (nuevaContrasena != null && !nuevaContrasena.isEmpty()) {
                ps.setString(3, nuevaContrasena);
                ps.setInt(4, usuario.getId());
            } else {
                ps.setInt(3, usuario.getId());
            }

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
