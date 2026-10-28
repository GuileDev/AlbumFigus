package logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión con MariaDB (Patrón Singleton).
 */
public class Conexion {
    private static Conexion instancia;

    // Atributos de configuración
    private String url;
    private String usuario;
    private String password;

    // Constructor privado (Patrón Singleton)
    private Conexion() {
        String nomBaseDatos = "pruebas";
        // TCP por el puerto 3306 (puedes cambiar a socket si lo prefieres)
        url = "jdbc:mariadb://localhost:3306/" + nomBaseDatos;
        usuario = "root";
        password = "1234";
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConnection() throws SQLException {
        // DriverManager carga el driver automáticamente (JDBC 4.0+)
        return DriverManager.getConnection(url, usuario, password);
    }

    public void cerrarConexion(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
