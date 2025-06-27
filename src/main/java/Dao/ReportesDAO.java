
package Dao;

import Config.clsConnection;
import Model.Reporte;
import java.sql.*;
import java.util.*;

public class ReportesDAO {

    public List<Reporte> obtenerReportesPorUsuario(int usuarioId) {
        List<Reporte> lista = new ArrayList<>();

        String sql = "SELECT r.*, "
                + "u.nombre AS nombre_estudiante, "
                + "s.nombre AS nombre_salon, "
                + "p.nombre AS nombre_periodo "
                + "FROM reportes r "
                + "JOIN estudiantes e ON r.estudiante_id = e.id "
                + "JOIN usuarios u ON e.usuario_id = u.id "
                + "JOIN salones s ON r.salon_id = s.id "
                + "JOIN periodos_academicos p ON r.periodo_id = p.id "
                + "WHERE r.usuario_id = ? "
                + "ORDER BY r.fecha_generacion DESC";

        try (Connection conn = clsConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reporte r = new Reporte();
                r.setId(rs.getInt("id"));
                r.setEstudianteId(rs.getInt("estudiante_id"));
                r.setSalonId(rs.getInt("salon_id"));
                r.setPeriodoId(rs.getInt("periodo_id"));
                r.setPromedioGeneral(rs.getDouble("promedio_general"));
                r.setEstadoAcademico(rs.getString("estado_academico"));
                r.setFechaGeneracion(rs.getTimestamp("fecha_generacion").toLocalDateTime());
                r.setUsuarioId(rs.getInt("usuario_id"));
                r.setPdf(rs.getBytes("pdf"));

                // Los campos nuevos con nombres ya procesados
                r.setNombreEstudiante(rs.getString("nombre_estudiante"));
                r.setNombreSalon(rs.getString("nombre_salon"));
                r.setNombrePeriodo(rs.getString("nombre_periodo"));

                lista.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean insertarReporte(Reporte r) {
        String sql = "INSERT INTO reportes (estudiante_id, salon_id, periodo_id, usuario_id, promedio_general, estado_academico, pdf) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = clsConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getEstudianteId());
            ps.setInt(2, r.getSalonId());
            ps.setInt(3, r.getPeriodoId());
            ps.setInt(4, r.getUsuarioId());
            ps.setDouble(5, r.getPromedioGeneral());
            ps.setString(6, r.getEstadoAcademico());
            ps.setBytes(7, r.getPdf());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public byte[] obtenerPDF(int reporteId) {
        String sql = "SELECT pdf FROM reportes WHERE id = ?";
        try (Connection conn = clsConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reporteId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBytes("pdf");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
