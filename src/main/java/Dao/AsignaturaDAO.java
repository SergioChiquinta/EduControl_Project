
package Dao;

import Model.Asignatura;
import Config.clsConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AsignaturaDAO {

    public List<Asignatura> obtenerAsignaturasPorDocente(int docenteId) {
        List<Asignatura> asignaturas = new ArrayList<>();
        String sql = "SELECT id, nombre, docente_id FROM materias WHERE docente_id = ?";
        try (Connection con = clsConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, docenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Asignatura asignatura = new Asignatura();
                    asignatura.setId(rs.getInt("id"));
                    asignatura.setNombre(rs.getString("nombre"));
                    asignatura.setDocenteId(rs.getInt("docente_id"));
                    asignaturas.add(asignatura);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asignaturas;
    }

}
