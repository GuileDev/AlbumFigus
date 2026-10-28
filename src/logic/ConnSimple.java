package logic;
import java.sql.Connection;

/**
 * Clase principal para probar la conexión
 */
public class ConnSimple {

    public static void main(String[] args) {
        try {
            
            Conexion conexion = Conexion.getInstancia();
            Connection con = conexion.getConnection();

            System.out.println("Conexión exitosa a la base de datos.");

            // Buena práctica: cerrar la conexión tras finalizar
            con.close();

        } catch (Exception e) {
            System.out.println("No se pudo conectar a la base de datos.");
            System.out.println("Error: " + e.getMessage());
        }
    }
}