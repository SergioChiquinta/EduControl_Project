
package Dao;

import Config.clsConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EstudianteDAO {

    private final Connection conexion;

    public EstudianteDAO() throws SQLException {
        this.conexion = clsConnection.getConnection();
    }

    // Obtener nombre del salón del estudiante
    public String obtenerSalonActual(int usuarioId) throws SQLException {
        String sql = "SELECT s.nombre FROM estudiantes e JOIN salones s ON e.salon_id = s.id WHERE e.usuario_id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }
        }
        return "No asignado";
    }

    public Map<String, List<String>> obtenerEvaluacionesAgrupadas(int usuarioId) throws SQLException {
        Map<String, List<String>> evaluacionesPorMateria = new LinkedHashMap<>();

        String sql
                = "SELECT m.nombre AS materia_nombre, ev.nombre AS evaluacion_nombre "
                + "FROM estudiantes e "
                + "JOIN salones s ON e.salon_id = s.id "
                + "JOIN cursos c ON c.salon_id = s.id AND c.activo = TRUE "
                + "JOIN materias m ON c.materia_id = m.id "
                + "JOIN evaluaciones ev ON ev.curso_id = c.id "
                + "WHERE e.usuario_id = ? "
                + "ORDER BY m.nombre, "
                + "  CASE "
                + "    WHEN ev.nombre LIKE 'PC1' THEN 1 "
                + "    WHEN ev.nombre LIKE 'PC2' THEN 2 "
                + "    WHEN ev.nombre LIKE 'Proyecto' THEN 3 "
                + "    WHEN ev.nombre LIKE 'Examen Parcial' THEN 4 "
                + "    WHEN ev.nombre LIKE 'Examen Final' THEN 5 "
                + "    WHEN ev.nombre LIKE 'Tarea%' THEN 6 "
                + "    ELSE 99 "
                + "  END";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String materia = rs.getString("materia_nombre");
                    String evaluacion = rs.getString("evaluacion_nombre");

                    // agrupar en el Map
                    if (!evaluacionesPorMateria.containsKey(materia)) {
                        evaluacionesPorMateria.put(materia, new ArrayList<>());
                    }
                    evaluacionesPorMateria.get(materia).add(evaluacion);
                }
            }
        }

        return evaluacionesPorMateria;
    }
}
