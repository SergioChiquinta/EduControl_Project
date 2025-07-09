
package Dao;

import Config.clsConnection;
import Model.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursosAdminDAO {

    public List<Curso> obtenerCursos() {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT c.id, m.nombre AS nombreMateria, s.nombre AS nombreSalon, " +
                     "p.nombre AS nombrePeriodo, u.nombre AS nombreDocente " +
                     "FROM cursos c " +
                     "JOIN materias m ON c.materia_id = m.id " +
                     "JOIN salones s ON c.salon_id = s.id " +
                     "JOIN periodos_academicos p ON c.periodo_id = p.id " +
                     "JOIN docentes d ON m.docente_id = d.id " +
                     "JOIN usuarios u ON d.usuario_id = u.id";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Curso c = new Curso();
                c.setId(rs.getInt("id"));
                c.setNombreMateria(rs.getString("nombreMateria"));
                c.setNombreSalon(rs.getString("nombreSalon"));
                c.setNombrePeriodo(rs.getString("nombrePeriodo"));
                c.setDocenteNombre(rs.getString("nombreDocente"));
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void crearCurso(int materiaId, int salonId, int periodoId) {
        String sql = "INSERT INTO cursos (materia_id, salon_id, periodo_id) VALUES (?, ?, ?)";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, materiaId);
            ps.setInt(2, salonId);
            ps.setInt(3, periodoId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarCurso(int id) {
        String sql = "DELETE FROM cursos WHERE id = ?";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String[]> obtenerPeriodos() {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM periodos_academicos";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new String[]{String.valueOf(rs.getInt("id")), rs.getString("nombre")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<String[]> obtenerMaterias() {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM materias";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new String[]{String.valueOf(rs.getInt("id")), rs.getString("nombre")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<String[]> obtenerSalones() {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM salones";
        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new String[]{String.valueOf(rs.getInt("id")), rs.getString("nombre")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
