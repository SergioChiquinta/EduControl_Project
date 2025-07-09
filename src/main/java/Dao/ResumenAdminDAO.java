
package Dao;

import Model.LogActividad;
import Model.ResumenAdmin;
import Config.clsConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ResumenAdminDAO {
    private Connection conexion;

    public ResumenAdminDAO() {
        try {
            this.conexion = clsConnection.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener conexión a la base de datos", e);
        }
    }
    
    public ResumenAdmin obtenerResumenAdmin() {
        ResumenAdmin resumen = new ResumenAdmin();

        try (Connection conn = clsConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Obtener cantidad de docentes
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM docentes")) {
                if (rs.next()) resumen.setTotalDocentes(rs.getInt("total"));
            }

            // Obtener cantidad de estudiantes
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM estudiantes")) {
                if (rs.next()) resumen.setTotalEstudiantes(rs.getInt("total"));
            }

            // Obtener cantidad de usuarios totales
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM usuarios")) {
                if (rs.next()) resumen.setTotalUsuarios(rs.getInt("total"));
            }

        } catch (SQLException e) {
            // Establecer valores por defecto en caso de error
            resumen.setTotalDocentes(0);
            resumen.setTotalEstudiantes(0);
            resumen.setTotalUsuarios(0);
            System.err.println("Error al obtener resumen: " + e.getMessage());
        }

        return resumen;
    }
    
    // Obtener logs recientes
    public List<LogActividad> obtenerLogsRecientes(int limit) {
        List<LogActividad> logs = new ArrayList<>();

        try (Connection conn = clsConnection.getConnection()) {
            String sql = "{CALL obtener_logs_recientes(?)}";
            CallableStatement cs = conn.prepareCall(sql);
            cs.setInt(1, limit);
            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                LogActividad log = new LogActividad();
                log.setId(rs.getInt("id"));
                log.setUsuario(rs.getString("usuario")); // ahora ya viene directo
                log.setCorreo(rs.getString("correo"));
                log.setAccion(rs.getString("accion"));
                log.setDescripcion(rs.getString("descripcion"));
                log.setFechaRegistro(rs.getTimestamp("fecha_registro"));

                logs.add(log);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener logs de actividad", e);
        }

        return logs;
    }
    
    // Método para registrar logs de actividad (versión final)
    public void registrarActividad(int usuarioId, String accion, String descripcion, String ip) {
        String sqlUsuario = "SELECT nombre, correo FROM usuarios WHERE id = ?";
        String nombre = "";
        String correo = "";

        try (Connection conn = clsConnection.getConnection();
             PreparedStatement psUser = conn.prepareStatement(sqlUsuario)) {

            psUser.setInt(1, usuarioId);
            try (ResultSet rs = psUser.executeQuery()) {
                if (rs.next()) {
                    nombre = rs.getString("nombre");
                    correo = rs.getString("correo");
                }
            }

            String sql = "CALL registrar_log(?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, usuarioId);
                ps.setString(2, nombre);
                ps.setString(3, correo);
                ps.setString(4, accion);
                ps.setString(5, descripcion);
                ps.setString(6, ip);
                ps.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Cerrar conexión si es necesario
    public void close() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
