
package Dao;

import Config.clsConnection;
import Model.Asignatura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsignaturasAdminDAO {

    public List<Asignatura> obtenerAsignaturas() {
        List<Asignatura> lista = new ArrayList<>();
        String sql = "SELECT a.id, a.nombre, d.usuario_id, u.nombre as docenteNombre " +
                     "FROM materias a INNER JOIN docentes d ON a.docente_id = d.id " +
                     "INNER JOIN usuarios u ON d.usuario_id = u.id";

        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Asignatura a = new Asignatura();
                a.setId(rs.getInt("id"));
                a.setNombre(rs.getString("nombre"));
                a.setDocenteId(rs.getInt("usuario_id"));
                a.setDocenteNombre(rs.getString("docenteNombre"));
                lista.add(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<String[]> obtenerDocentesDisponibles() {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT u.id, u.nombre FROM usuarios u " +
                     "INNER JOIN docentes d ON u.id = d.usuario_id " +
                     "WHERE d.id NOT IN (SELECT docente_id FROM materias)";

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

    public boolean crearAsignatura(String nombre, int docenteId) {
        String sql = "INSERT INTO materias (nombre, docente_id) VALUES (?, (SELECT id FROM docentes WHERE usuario_id = ?))";

        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, docenteId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editarAsignatura(int id, String nombre, int docenteId) {
        String sql = "UPDATE materias SET nombre = ?, docente_id = (SELECT id FROM docentes WHERE usuario_id = ?) WHERE id = ?";

        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, docenteId);
            ps.setInt(3, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarAsignatura(int id) {
        String sql = "DELETE FROM materias WHERE id = ?";

        try (Connection conn = clsConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
