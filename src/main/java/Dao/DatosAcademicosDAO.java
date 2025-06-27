
package Dao;

import Config.clsConnection;
import java.sql.*;
import java.util.*;

public class DatosAcademicosDAO {

    public List<Map<String, String>> obtenerPeriodos() {
        List<Map<String, String>> periodos = new ArrayList<>();
        String sql = "SELECT id, nombre FROM periodos_academicos";
        try (Connection conn = clsConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("id", rs.getString("id"));
                map.put("nombre", rs.getString("nombre"));
                periodos.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return periodos;
    }

    public List<Map<String, String>> obtenerSalonesPorPeriodo(int periodoId) {
        List<Map<String, String>> salones = new ArrayList<>();
        String sql = "SELECT DISTINCT s.id, s.nombre FROM cursos c JOIN salones s ON c.salon_id = s.id WHERE c.periodo_id = ?";
        try (Connection conn = clsConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, periodoId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("id", rs.getString("id"));
                map.put("nombre", rs.getString("nombre"));
                salones.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salones;
    }

    public List<Map<String, String>> obtenerEstudiantesPorSalon(int salonId) {
        List<Map<String, String>> estudiantes = new ArrayList<>();
        String sql = "SELECT e.id, u.nombre FROM estudiantes e JOIN usuarios u ON e.usuario_id = u.id WHERE e.salon_id = ?";
        try (Connection conn = clsConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, salonId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("id", rs.getString("id"));
                map.put("nombre", rs.getString("nombre"));
                estudiantes.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estudiantes;
    }

    public double calcularPromedioEstudiante(int estudianteId, int periodoId) {
        String sql = "SELECT AVG(promedio_materia) AS promedio_general "
                + "FROM ( "
                + "    SELECT  "
                + "        SUM(IFNULL(n.nota, 0) * e.peso) / SUM(e.peso) AS promedio_materia "
                + "    FROM cursos c "
                + "    JOIN evaluaciones e ON c.materia_id = e.materia_id AND c.periodo_id = e.periodo_id "
                + "    LEFT JOIN notas n ON n.evaluacion_id = e.id AND n.estudiante_id = ? "
                + "    WHERE c.periodo_id = ? "
                + "      AND c.salon_id = (SELECT salon_id FROM estudiantes WHERE id = ?) "
                + "    GROUP BY c.materia_id "
                + ") AS promedios_por_materia;";

        try (Connection conn = clsConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, estudianteId);
            ps.setInt(2, periodoId);
            ps.setInt(3, estudianteId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("promedio_general");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public String obtenerNombre(String tabla, int id) {
        String sql;

        if ("usuarios_estudiante".equals(tabla)) {
            sql = "SELECT u.nombre FROM usuarios u\n"
                    + "JOIN estudiantes e ON e.usuario_id = u.id\n"
                    + "WHERE e.id = ?";
        } else {
            sql = "SELECT nombre FROM " + tabla + " WHERE id = ?";
        }

        try (Connection conn = clsConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public Map<String, Double> calcularPromediosPorMateria(int estudianteId, int periodoId) {
        Map<String, Double> promedios = new LinkedHashMap<>();

        String sql = "SELECT " +
             "    m.nombre AS materia_nombre, " +
             "    SUM(COALESCE(n.nota, 0) * e.peso) / SUM(e.peso) AS promedio " +
             "FROM evaluaciones e " +
             "JOIN materias m ON e.materia_id = m.id " +
             "LEFT JOIN notas n ON n.evaluacion_id = e.id AND n.estudiante_id = ? " +
             "WHERE e.periodo_id = ? " +
             "GROUP BY m.id, m.nombre " +
             "ORDER BY m.nombre";

        try (Connection conn = clsConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, estudianteId);
            ps.setInt(2, periodoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String materia = rs.getString("materia_nombre");
                double promedio = rs.getDouble("promedio");
                promedios.put(materia, promedio);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return promedios;
    }

}
