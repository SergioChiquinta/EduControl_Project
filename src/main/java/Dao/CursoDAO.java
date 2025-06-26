
package Dao;

import Model.Curso;
import Config.clsConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public List<Curso> obtenerCursosPorDocente(int docenteId) {
        List<Curso> cursos = new ArrayList<>();
        // Asumiendo que el docente está asociado a la materia (tabla materias.docente_id)
        // y un curso está asociado a una materia.
        // Si necesitas el conteo de estudiantes, debes tener una tabla de inscripciones
        // Por ahora, asumiré 0 estudiantes o una columna ficticia si no tienes la tabla de inscripciones.
        String sql = "SELECT "
                + "c.id AS curso_id, "
                + "m.nombre AS nombre_materia, "
                + "s.nombre AS nombre_salon, "
                + "pa.nombre AS nombre_periodo, "
                + "COUNT(e.id) AS estudiantes_inscritos "
                + "FROM cursos c "
                + "JOIN materias m ON c.materia_id = m.id "
                + "JOIN salones s ON c.salon_id = s.id "
                + "JOIN periodos_academicos pa ON c.periodo_id = pa.id "
                + "LEFT JOIN estudiantes e ON e.salon_id = s.id "
                + "WHERE m.docente_id = ? "
                + "GROUP BY c.id, m.nombre, s.nombre, pa.nombre";
        try (Connection con = clsConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, docenteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Curso curso = new Curso();
                    curso.setId(rs.getInt("curso_id"));
                    curso.setNombreMateria(rs.getString("nombre_materia"));
                    curso.setNombreSalon(rs.getString("nombre_salon"));
                    curso.setNombrePeriodo(rs.getString("nombre_periodo"));
                    curso.setEstudiantesInscritos(rs.getInt("estudiantes_inscritos"));
                    cursos.add(curso);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;

    }

}
