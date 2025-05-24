
package Dao;

import Config.clsConnection;
import Model.Usuario;
import java.sql.*;

public class UsuarioDAO {
    public Usuario validarUsuario(String nombre, String clave) {
        String sql = "SELECT * FROM usuario WHERE Nom_Usu = ? AND Contraseña_Usu = SHA2(?, 256)";
        Connection conexion = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conexion = clsConnection.getConnection();
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, clave);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario u = new Usuario();
                u.setIDUsu(rs.getInt("ID_Usu"));
                u.setNomUsu(rs.getString("Nom_Usu"));
                u.setRolUsu(rs.getString("Rol_Usu"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al validar usuario", e);
        } finally {
            // Cerrar recursos en orden inverso
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* ignorar */ }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { /* ignorar */ }
            try { if (conexion != null) conexion.close(); } catch (SQLException e) { /* ignorar */ }
        }
        return null;
    }
}

