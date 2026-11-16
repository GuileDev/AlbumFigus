package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * gestiona la conexion con la base de datos (patron singleton).
 *
 * @author gerrz
 */
public class Conexion {
    private static Conexion instancia;

    /**
     * constructor privado.
     */
    private Conexion() {
    }

    /**
     * devuelve la instancia unica.
     *
     * @return instancia de conexion
     */
    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    /**
     * obtiene una conexion a la base de datos.
     *
     * @return conexion abierta
     * @throws Exception si falla la carga del driver o la conexion
     */
    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/album?allowPublicKeyRetrieval=true", "root", "1234");
    }
}