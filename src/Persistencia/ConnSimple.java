package Persistencia;

import java.sql.Connection;

/**
 * clase para probar la conexion a la base de datos.
 *
 * @author gerrz
 */
public class ConnSimple {

    /**
     * prueba la conexion e imprime el resultado.
     *
     * @param argumentos argumentos de linea de comandos (no se usan)
     */
    public static void main(String[] argumentos) {
        try {
            Conexion laConexion = Conexion.getInstancia();
            Connection conexionBD = laConexion.getConnection();

            System.out.println("conexion exitosa a la base de datos.");

            conexionBD.close();

        } catch (Exception excepcion) {
            System.out.println("no se pudo conectar a la base de datos.");
            System.out.println("error: " + excepcion.getMessage());
        }
    }
}