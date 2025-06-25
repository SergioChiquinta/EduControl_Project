
package Dao;

import Config.clsConnection;
import Model.Reporte;
import java.sql.*;
import java.util.*;

public class ReportesDAO {

    public boolean guardarReporte(Reporte r) {
        String sql = "INSERT INTO reportes(estudiante_id, salon_id, periodo_id, promedio_general, estado_academico, pdf) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getEstudianteId());
            ps.setInt(2, r.getSalonId());
            ps.setInt(3, r.getPeriodoId());
            ps.setDouble(4, r.getPromedioGeneral());
            ps.setString(5, r.getEstadoAcademico());
            ps.setBytes(6, r.getPdf());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error al guardar reporte: " + e.getMessage());
            return false;
        }
    }

    public List<Reporte> listarReportes() {
        List<Reporte> lista = new ArrayList<>();
        String sql = "SELECT r.id, r.promedio_general, r.estado_academico, r.fecha_generacion, " +
             "u.nombre AS nombre_estudiante, s.nombre AS nombre_salon, p.nombre AS nombre_periodo " +
             "FROM reportes r " +
             "JOIN estudiantes e ON r.estudiante_id = e.id " +
             "JOIN usuarios u ON e.usuario_id = u.id " +
             "JOIN salones s ON r.salon_id = s.id " +
             "JOIN periodos_academicos p ON r.periodo_id = p.id";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Reporte r = new Reporte();
                r.setId(rs.getInt("id"));
                r.setPromedioGeneral(rs.getDouble("promedio_general"));
                r.setEstadoAcademico(rs.getString("estado_academico"));
                r.setFechaGeneracion(rs.getString("fecha_generacion"));
                r.setNombreEstudiante(rs.getString("nombre_estudiante"));
                r.setNombreSalon(rs.getString("nombre_salon"));
                r.setNombrePeriodo(rs.getString("nombre_periodo"));
                lista.add(r);
            }
        } catch (Exception e) {
            System.out.println("Error al listar reportes: " + e.getMessage());
        }
        return lista;
    }

    public byte[] obtenerPDF(int idReporte) {
        String sql = "SELECT pdf FROM reportes WHERE id = ?";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idReporte);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("pdf");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener PDF: " + e.getMessage());
        }
        return null;
    }
}
