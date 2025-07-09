
package Dao;

import Config.clsConnection;
import Model.AsignaturaNota;
import Model.EvaluacionNota;
import java.sql.*;
import java.util.*;

public class EstudianteNotasDAO {

    public List<AsignaturaNota> obtenerNotasPorEstudiante(int estudianteId) {
        List<AsignaturaNota> lista = new ArrayList<>();

        String sqlMaterias = "SELECT DISTINCT m.id, m.nombre "
                + "FROM materias m "
                + "INNER JOIN cursos c ON c.materia_id = m.id "
                + "INNER JOIN estudiantes est ON est.salon_id = c.salon_id "
                + "WHERE est.id = ?";

        try (Connection con = clsConnection.getConnection(); PreparedStatement psMaterias = con.prepareStatement(sqlMaterias)) {

            psMaterias.setInt(1, estudianteId);
            ResultSet rsMaterias = psMaterias.executeQuery();

            while (rsMaterias.next()) {
                AsignaturaNota asignatura = new AsignaturaNota();
                asignatura.setId(rsMaterias.getInt("id"));
                asignatura.setNombreMateria(rsMaterias.getString("nombre"));

                // Evaluaciones de la materia
                List<EvaluacionNota> evaluaciones = new ArrayList<>();
                String sqlEvaluaciones = "SELECT e.nombre AS nombre_evaluacion, e.peso, n.nota "
                        + "FROM evaluaciones e "
                        + "INNER JOIN cursos c ON e.curso_id = c.id "
                        + "LEFT JOIN notas n ON n.evaluacion_id = e.id AND n.estudiante_id = ? "
                        + "WHERE c.materia_id = ?";

                try (PreparedStatement psEval = con.prepareStatement(sqlEvaluaciones)) {
                    psEval.setInt(1, estudianteId);
                    psEval.setInt(2, asignatura.getId());
                    ResultSet rsEval = psEval.executeQuery();

                    double promedio = 0.0;
                    boolean todasCalificadas = true;
                    boolean algunaCalificada = false;

                    while (rsEval.next()) {
                        EvaluacionNota eval = new EvaluacionNota();
                        eval.setNombreEvaluacion(rsEval.getString("nombre_evaluacion"));
                        eval.setPeso(rsEval.getDouble("peso"));

                        Double nota = rsEval.getObject("nota") != null ? rsEval.getDouble("nota") : null;
                        if (nota != null) {
                            eval.setNota(nota);
                            promedio += (nota * eval.getPeso() / 100.0);
                            algunaCalificada = true;

                            if (nota >= 11) {
                                eval.setEstado("Aprobado");
                                eval.setEstadoColor("text-green-600");
                            } else {
                                eval.setEstado("Reprobado");
                                eval.setEstadoColor("text-red-600");
                            }
                        } else {
                            eval.setNota(0.0);
                            eval.setEstado("Pendiente");
                            eval.setEstadoColor("text-yellow-600");
                            todasCalificadas = false;
                        }

                        evaluaciones.add(eval);
                    }

                    promedio = Math.round(promedio * 100.0) / 100.0;
                    asignatura.setEvaluaciones(evaluaciones);
                    asignatura.setPromedioMateria(promedio);

                    // Determinar estado general
                    String estadoGeneral = "En progreso";
                    if (todasCalificadas) {
                        if (promedio >= 11) {
                            estadoGeneral = "Aprobado";
                        } else {
                            estadoGeneral = "Reprobado";
                        }
                    } else if (!algunaCalificada) {
                        estadoGeneral = "Pendiente";
                    }

                    asignatura.setEstadoGeneral(estadoGeneral);
                }

                lista.add(asignatura);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public int obtenerEstudianteIdPorUsuarioId(int usuarioId) {
        int estudianteId = 0;
        String sql = "SELECT id FROM estudiantes WHERE usuario_id = ?";
        try (Connection con = clsConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                estudianteId = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estudianteId;
    }

}
