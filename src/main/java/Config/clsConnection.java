
package Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class clsConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/educontrol_db";
    private static final String USER = "root";
    private static final String PASS = ""; // cambia si pusiste contraseña
    static {
        try {
            // Registrar el driver explícitamente
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error al cargar el driver JDBC de MySQL", e);
        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
