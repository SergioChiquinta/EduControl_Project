
package Dao;

import Config.clsConnection;
import Model.Curso;
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
        String sql = "SELECT DISTINCT CONCAT(s.nombre, ' - ', p.nombre) AS salon_periodo "
                + "FROM salones s "
                + "JOIN cursos c ON s.id = c.salon_id "
                + "JOIN materias m ON c.materia_id = m.id "
                + "JOIN periodos_academicos p ON c.periodo_id = p.id "
                + "WHERE m.docente_id = ? AND c.activo = TRUE";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, docenteId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                salones.add(rs.getString("salon_periodo"));
            }
        }
        return salones;
    }

    // Obtener Cursos Activos Por Periodo y Docente
    public List<Curso> obtenerCursosActivosPorDocente(int docenteId) throws SQLException {
        List<Curso> lista = new ArrayList<>();
        String sql = "SELECT c.*, m.nombre AS nombre_materia, s.nombre AS nombre_salon, p.nombre AS nombre_periodo "
                + "FROM cursos c "
                + "INNER JOIN materias m ON c.materia_id = m.id "
                + "INNER JOIN salones s ON c.salon_id = s.id "
                + "INNER JOIN periodos_academicos p ON c.periodo_id = p.id "
                + "WHERE m.docente_id = ? AND c.activo = TRUE";

        try (Connection con = clsConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, docenteId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Curso curso = new Curso();
                curso.setId(rs.getInt("id"));
                curso.setNombreMateria(rs.getString("nombre_materia"));
                curso.setNombreSalon(rs.getString("nombre_salon"));
                curso.setNombrePeriodo(rs.getString("nombre_periodo"));
                curso.setActivo(rs.getBoolean("activo"));
                lista.add(curso);
            }
        }
        return lista;
    }

    // Obtener estudiantes con notas por salon y evaluación
    public List<EstudianteNotaDTO> obtenerEstudiantesConNotas(String nombreSalon, String nombrePeriodo, int evaluacionId) throws SQLException {
        List<EstudianteNotaDTO> estudiantes = new ArrayList<>();

        // Consulta SQL mejorada que verifica que la evaluación pertenece al curso del salón
        String sql = "SELECT e.id AS estudiante_id, u.nombre AS nombre_estudiante, n.nota "
        + "FROM estudiantes e "
        + "JOIN usuarios u ON e.usuario_id = u.id "
        + "JOIN salones s ON e.salon_id = s.id "
        + "JOIN cursos c ON c.salon_id = s.id "
        + "JOIN periodos_academicos p ON c.periodo_id = p.id "
        + "JOIN evaluaciones ev ON ev.curso_id = c.id AND ev.id = ? "
        + "LEFT JOIN notas n ON e.id = n.estudiante_id AND n.evaluacion_id = ? "
        + "WHERE s.nombre = ? AND p.nombre = ? "
        + "AND c.activo = TRUE";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, evaluacionId);
            ps.setInt(2, evaluacionId);
            ps.setString(3, nombreSalon);
            ps.setString(4, nombrePeriodo);
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

    // Obtener evaluaciones por docente (actualizado)
    public List<Evaluacion> obtenerEvaluacionesPorDocente(int docenteId) throws SQLException {
        List<Evaluacion> lista = new ArrayList<>();

        String sql = "SELECT ev.id, ev.nombre, ev.peso, ev.curso_id, ev.tipo_id "
                + "FROM evaluaciones ev "
                + "INNER JOIN cursos c ON ev.curso_id = c.id "
                + "INNER JOIN materias m ON c.materia_id = m.id "
                + "WHERE m.docente_id = ? AND c.activo = TRUE";

        try (Connection con = clsConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, docenteId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Evaluacion evaluacion = new Evaluacion();
                evaluacion.setId(rs.getInt("id"));
                evaluacion.setNombre(rs.getString("nombre"));
                evaluacion.setPeso(rs.getDouble("peso"));
                evaluacion.setCursoId(rs.getInt("curso_id"));
                evaluacion.setTipoId(rs.getInt("tipo_id"));
                lista.add(evaluacion);
            }
        }
        return lista;
    }

}
