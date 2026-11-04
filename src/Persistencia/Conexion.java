package Persistencia;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Clase encargada de gestionar la conexión con MariaDB
 */
public class Conexion {
    private static Conexion instancia;

    // Atributos de configuración
    private Connection con;
    private String driver;
    private String url;
    private String usuario;
    private String password;
    private String nomBaseDatos;

    // Constructor privado (Patrón Singleton)
    private Conexion() {
        nomBaseDatos = "pruebas";
        driver = "org.mariadb.jdbc.Driver";
        // Puerto configurado en 3306
        url = "jdbc:mariadb://localhost:3306/" + nomBaseDatos + "?sslMode=trust";
        usuario = "albumfigus";
        password = "";
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConnection() throws Exception {
        Class.forName(driver); // Carga dinámica del driver
        con = DriverManager.getConnection(url, usuario, password);
        return con;
    }
}