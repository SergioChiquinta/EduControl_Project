
package Dao;

import Config.clsConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;


public class NotaDAO {

    public boolean agregarNota(Date fecha, double calificacion, int codigoStu, int codigoDcte) {
        String sql = "{CALL AgregarNota(?, ?, ?, ?)}";
        try (Connection con = clsConnection.getConnection();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setDate(1, new java.sql.Date(fecha.getTime()));
            stmt.setDouble(2, calificacion);
            stmt.setInt(3, codigoStu);
            stmt.setInt(4, codigoDcte);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizarNota(int idNota, Date fecha, double calificacion) {
        String sql = "{CALL ActualizarNota(?, ?, ?)}";
        try (Connection con = clsConnection.getConnection();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setInt(1, idNota);
            stmt.setDate(2, new java.sql.Date(fecha.getTime()));
            stmt.setDouble(3, calificacion);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarNota(int idNota) {
        String sql = "{CALL EliminarNota(?)}";
        try (Connection con = clsConnection.getConnection();
             CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setInt(1, idNota);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

