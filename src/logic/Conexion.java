package logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión con MySQL (Patrón Singleton).
 *
 * Los valores por defecto apuntan a la base "albumfigus" creada con el
 * script db/albumfigus.sql. Se pueden sobrescribir sin recompilar usando:
 *
 *   -Dalbumfigus.db.url=jdbc:mysql://localhost:3306/albumfigus
 *   -Dalbumfigus.db.usuario=albumfigus
 *   -Dalbumfigus.db.password=albumfigus2026
 *
 * o las variables de entorno ALBUMFIGUS_DB_URL, ALBUMFIGUS_DB_USUARIO
 * y ALBUMFIGUS_DB_PASSWORD.
 */
public class Conexion {

    private static final String URL_POR_DEFECTO
            = "jdbc:mysql://localhost:3306/albumfigus"
            + "?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";

    private static final String USUARIO_POR_DEFECTO = "albumfigus";
    private static final String PASSWORD_POR_DEFECTO = "albumfigus2026";

    private static Conexion instancia;

    private final String url;
    private final String usuario;
    private final String password;

    // Constructor privado (Patrón Singleton)
    private Conexion() {
        this.url = leerConfiguracion("albumfigus.db.url", "ALBUMFIGUS_DB_URL", URL_POR_DEFECTO);
        this.usuario = leerConfiguracion("albumfigus.db.usuario", "ALBUMFIGUS_DB_USUARIO", USUARIO_POR_DEFECTO);
        this.password = leerConfiguracion("albumfigus.db.password", "ALBUMFIGUS_DB_PASSWORD", PASSWORD_POR_DEFECTO);
    }

    public static synchronized Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    /**
     * Devuelve una conexión nueva a MySQL. Quien la obtiene debe cerrarla
     * (preferentemente con try-with-resources).
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, usuario, password);
    }

    /**
     * Comprueba que la base de datos responde. Útil para mostrar un aviso
     * al iniciar la aplicación.
     */
    public boolean probarConexion() {
        try (Connection con = getConnection()) {
            return con != null && con.isValid(3);
        } catch (SQLException e) {
            System.err.println("No se pudo conectar a MySQL: " + e.getMessage());
            return false;
        }
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

    public String getUrl() {
        return url;
    }

    public String getUsuario() {
        return usuario;
    }

    private static String leerConfiguracion(String propiedad, String variableEntorno, String porDefecto) {
        String valor = System.getProperty(propiedad);
        if (valor == null || valor.isBlank()) {
            valor = System.getenv(variableEntorno);
        }
        return (valor == null || valor.isBlank()) ? porDefecto : valor.trim();
    }
}
