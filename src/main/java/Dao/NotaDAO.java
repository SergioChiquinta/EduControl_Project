
package Dao;

import Config.clsConnection;
import Model.Evaluacion;
import Model.Nota;
import Model.EstudianteNotaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    private Connection connection;

    public NotaDAO() throws SQLException {
        connection = clsConnection.getConnection();
    }

    // Obtener salones del docente
    public List<String> obtenerSalonesPorDocente(int docenteId) throws SQLException {
        List<String> salones = new ArrayList<>();
        String sql = "SELECT DISTINCT s.nombre FROM salones s "
                + "JOIN cursos c ON s.id = c.salon_id "
                + "JOIN materias m ON c.materia_id = m.id "
                + "WHERE m.docente_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, docenteId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                salones.add(rs.getString("nombre"));
            }
        }
        return salones;
    }

    // Obtener evaluaciones por materia del docente
    public List<Evaluacion> obtenerEvaluacionesPorDocente(int docenteId) throws SQLException {
        List<Evaluacion> evaluaciones = new ArrayList<>();
        String sql = "SELECT e.id, e.nombre, e.peso, e.materia_id, e.periodo_id, e.tipo_id "
                + "FROM evaluaciones e "
                + "JOIN materias m ON e.materia_id = m.id "
                + "WHERE m.docente_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, docenteId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Evaluacion eval = new Evaluacion();
                eval.setId(rs.getInt("id"));
                eval.setNombre(rs.getString("nombre"));
                eval.setPeso(rs.getDouble("peso"));
                eval.setMateriaId(rs.getInt("materia_id"));
                eval.setPeriodoId(rs.getInt("periodo_id"));
                eval.setTipoId(rs.getInt("tipo_id"));
                evaluaciones.add(eval);
            }
        }
        return evaluaciones;
    }

    // Obtener estudiantes con notas por salon y evaluación
    public List<EstudianteNotaDTO> obtenerEstudiantesConNotas(String nombreSalon, int evaluacionId) throws SQLException {
        List<EstudianteNotaDTO> estudiantes = new ArrayList<>();
        String sql = "SELECT e.id AS estudiante_id, u.nombre AS nombre_estudiante, n.nota "
                + "FROM estudiantes e "
                + "JOIN usuarios u ON e.usuario_id = u.id "
                + "JOIN salones s ON e.salon_id = s.id "
                + "LEFT JOIN notas n ON e.id = n.estudiante_id AND n.evaluacion_id = ? "
                + "WHERE s.nombre = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, evaluacionId);
            ps.setString(2, nombreSalon);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EstudianteNotaDTO dto = new EstudianteNotaDTO();
                dto.setEstudianteId(rs.getInt("estudiante_id"));
                dto.setNombreEstudiante(rs.getString("nombre_estudiante"));
                dto.setNota(rs.getObject("nota") != null ? rs.getDouble("nota") : null);
                estudiantes.add(dto);
            }
        }
        return estudiantes;
    }

    // Guardar o actualizar nota
    public boolean guardarNota(int estudianteId, int evaluacionId, double nota) throws SQLException {
        // Primero verificamos si ya existe una nota para este estudiante y evaluación
        String checkSql = "SELECT id FROM notas WHERE estudiante_id = ? AND evaluacion_id = ?";
        boolean existe = false;
        int notaId = 0;

        try (PreparedStatement checkPs = connection.prepareStatement(checkSql)) {
            checkPs.setInt(1, estudianteId);
            checkPs.setInt(2, evaluacionId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                existe = true;
                notaId = rs.getInt("id");
            }
        }

        String sql;
        if (existe) {
            sql = "UPDATE notas SET nota = ? WHERE id = ?";
        } else {
            sql = "INSERT INTO notas (estudiante_id, evaluacion_id, nota) VALUES (?, ?, ?)";
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (existe) {
                ps.setDouble(1, nota);
                ps.setInt(2, notaId);
            } else {
                ps.setInt(1, estudianteId);
                ps.setInt(2, evaluacionId);
                ps.setDouble(3, nota);
            }
            return ps.executeUpdate() > 0;
        }
    }
}
