
package Dao;

import Config.clsConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DocenteDAO {

    private final Connection conexion;

    public DocenteDAO() throws SQLException {
        this.conexion = clsConnection.getConnection();
    }

    // Contar asignaturas asignadas al docente
    public int contarAsignaturas(int docenteId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM materias WHERE docente_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, docenteId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public int contarCursosActivos(int docenteId) throws SQLException {
        String sql = "SELECT COUNT(*) "
                + "FROM cursos c "
                + "JOIN materias m ON c.materia_id = m.id "
                + "WHERE m.docente_id = ? AND c.activo = TRUE";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, docenteId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

}
